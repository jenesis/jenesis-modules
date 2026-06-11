package build.jenesis.crawler.model;

import module java.base;

public final class Version implements Comparable<Version> {

    private static final List<String> QUALIFIER_ORDER = List.of("alpha", "beta", "milestone", "rc", "snapshot", "", "sp");
    private static final int RELEASE_INDEX = QUALIFIER_ORDER.indexOf("");
    private static final Map<String, String> QUALIFIER_ALIASES = Map.of(
            "ga", "",
            "final", "",
            "release", "",
            "cr", "rc"
    );

    private final String raw;
    private final ListItem root;

    public Version(String raw) {
        this.raw = Objects.requireNonNull(raw, "version");
        this.root = parse(raw.toLowerCase(Locale.ROOT));
    }

    public String raw() {
        return raw;
    }

    @Override
    public int compareTo(Version other) {
        return root.compareTo(other.root);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Version other && compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        return root.hashCode();
    }

    @Override
    public String toString() {
        return raw;
    }

    private sealed interface Item extends Comparable<Item> permits NumberItem, QualifierItem, ListItem {
    }

    private record NumberItem(BigInteger value) implements Item {

        @Override
        public int compareTo(Item other) {
            return switch (other) {
                case NumberItem n -> value.compareTo(n.value);
                case QualifierItem q -> 1;
                case ListItem l -> 1;
            };
        }
    }

    private record QualifierItem(String value) implements Item {

        int rank() {
            int index = QUALIFIER_ORDER.indexOf(value);
            return index >= 0 ? index : QUALIFIER_ORDER.size();
        }

        @Override
        public int compareTo(Item other) {
            return switch (other) {
                case NumberItem n -> -1;
                case QualifierItem q -> {
                    int byRank = Integer.compare(rank(), q.rank());
                    yield byRank != 0 ? byRank : value.compareTo(q.value);
                }
                case ListItem l -> -1;
            };
        }
    }

    private record ListItem(List<Item> items) implements Item {

        @Override
        public int compareTo(Item other) {
            return switch (other) {
                case NumberItem n -> -1;
                case QualifierItem q -> 1;
                case ListItem l -> {
                    int max = Math.max(items.size(), l.items.size());
                    for (int i = 0; i < max; i++) {
                        Item a = i < items.size() ? items.get(i) : null;
                        Item b = i < l.items.size() ? l.items.get(i) : null;
                        int compared = compareNullable(a, b);
                        if (compared != 0) {
                            yield compared;
                        }
                    }
                    yield 0;
                }
            };
        }
    }

    private static int compareNullable(Item a, Item b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -compareToZero(b);
        }
        if (b == null) {
            return compareToZero(a);
        }
        return a.compareTo(b);
    }

    private static int compareToZero(Item item) {
        return switch (item) {
            case NumberItem n -> n.value.signum();
            case QualifierItem q -> {
                if (q.value.isEmpty()) {
                    yield 0;
                }
                yield q.rank() < RELEASE_INDEX ? -1 : 1;
            }
            case ListItem l -> {
                if (l.items.isEmpty()) {
                    yield 0;
                }
                yield compareToZero(l.items.getFirst());
            }
        };
    }

    private static ListItem parse(String raw) {
        Deque<List<Item>> stack = new ArrayDeque<>();
        List<Item> top = new ArrayList<>();
        stack.push(top);

        StringBuilder token = new StringBuilder();
        boolean digit = false;

        for (int index = 0; index < raw.length(); index++) {
            char character = raw.charAt(index);
            if (character == '+') {
                // Semantic-versioning build metadata: everything from the first '+'
                // onward is ignored for precedence (so "1.0.0+build1" and "1.0.0"
                // resolve as equal). The pre-release section after '-' is still parsed.
                break;
            } else if (character == '.') {
                emit(stack.peek(), token, false);
                token.setLength(0);
            } else if (character == '-') {
                emit(stack.peek(), token, false);
                token.setLength(0);
                List<Item> sublist = new ArrayList<>();
                stack.peek().add(new ListItem(sublist));
                stack.push(sublist);
            } else {
                boolean isDigit = Character.isDigit(character);
                if (token.length() > 0 && isDigit != digit) {
                    // A transition into a digit means the token we are emitting is an
                    // alphabetic qualifier immediately followed by a number.
                    emit(stack.peek(), token, isDigit);
                    token.setLength(0);
                }
                token.append(character);
                digit = isDigit;
            }
        }
        emit(stack.peek(), token, false);

        return new ListItem(normalize(top));
    }

    private static void emit(List<Item> target, StringBuilder token, boolean followedByDigit) {
        if (token.length() == 0) {
            return;
        }
        String value = token.toString();
        if (isNumeric(value)) {
            target.add(new NumberItem(new BigInteger(value)));
        } else {
            target.add(new QualifierItem(QUALIFIER_ALIASES.getOrDefault(value, expandShortQualifier(value, followedByDigit))));
        }
    }

    /**
     * Mirror Maven's {@code ComparableVersion}: a single-letter {@code a}, {@code b} or
     * {@code m} immediately followed by a digit is shorthand for {@code alpha}, {@code beta}
     * or {@code milestone} respectively. Without this, {@code 1-m1} would parse as an unknown
     * qualifier and sort <em>after</em> the release instead of before it.
     */
    private static String expandShortQualifier(String value, boolean followedByDigit) {
        if (!followedByDigit || value.length() != 1) {
            return value;
        }
        return switch (value.charAt(0)) {
            case 'a' -> "alpha";
            case 'b' -> "beta";
            case 'm' -> "milestone";
            default -> value;
        };
    }

    private static boolean isNumeric(String value) {
        for (int index = 0; index < value.length(); index++) {
            if (!Character.isDigit(value.charAt(index))) {
                return false;
            }
        }
        return !value.isEmpty();
    }

    private static List<Item> normalize(List<Item> items) {
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index) instanceof ListItem listItem) {
                items.set(index, new ListItem(normalize(new ArrayList<>(listItem.items))));
            }
        }
        while (!items.isEmpty() && compareToZero(items.getLast()) == 0) {
            items.removeLast();
        }
        return List.copyOf(items);
    }
}
