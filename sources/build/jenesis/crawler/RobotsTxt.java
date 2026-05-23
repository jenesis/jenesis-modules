package build.jenesis.crawler;

import module java.base;

public final class RobotsTxt {

    private RobotsTxt() {
    }

    public record Rules(List<String> disallow, List<String> allow) {

        public static final Rules ALLOW_ALL = new Rules(List.of(), List.of());

        public Rules {
            disallow = List.copyOf(disallow);
            allow = List.copyOf(allow);
        }

        public boolean allows(String path) {
            String normalized = path.startsWith("/") ? path : "/" + path;
            int allowMatch = longestPrefix(normalized, allow);
            int disallowMatch = longestPrefix(normalized, disallow);
            if (disallowMatch < 0) {
                return true;
            }
            return allowMatch >= disallowMatch;
        }

        private static int longestPrefix(String path, List<String> patterns) {
            int best = -1;
            for (String pattern : patterns) {
                if (pattern.isEmpty()) {
                    continue;
                }
                if (path.startsWith(pattern) && pattern.length() > best) {
                    best = pattern.length();
                }
            }
            return best;
        }
    }

    public static String agentToken(String userAgent) {
        int end = userAgent.length();
        int slash = userAgent.indexOf('/');
        if (slash >= 0) {
            end = Math.min(end, slash);
        }
        int space = userAgent.indexOf(' ');
        if (space >= 0) {
            end = Math.min(end, space);
        }
        return userAgent.substring(0, end);
    }

    public static Rules rulesFor(String content, String userAgentToken) {
        String target = userAgentToken.toLowerCase(Locale.ROOT);
        List<String> currentAgents = new ArrayList<>();
        List<String> specificDisallow = new ArrayList<>();
        List<String> specificAllow = new ArrayList<>();
        List<String> wildcardDisallow = new ArrayList<>();
        List<String> wildcardAllow = new ArrayList<>();
        boolean specificMatched = false;
        boolean awaitingNewGroup = false;
        for (String rawLine : content.split("\\r?\\n")) {
            String line = rawLine;
            int hash = line.indexOf('#');
            if (hash >= 0) {
                line = line.substring(0, hash);
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            int colon = line.indexOf(':');
            if (colon < 0) {
                continue;
            }
            String field = line.substring(0, colon).trim().toLowerCase(Locale.ROOT);
            String value = line.substring(colon + 1).trim();
            switch (field) {
                case "user-agent" -> {
                    if (awaitingNewGroup) {
                        currentAgents.clear();
                        awaitingNewGroup = false;
                    }
                    currentAgents.add(value.toLowerCase(Locale.ROOT));
                }
                case "disallow", "allow" -> {
                    awaitingNewGroup = true;
                    boolean specific = currentAgents.contains(target);
                    boolean wildcard = currentAgents.contains("*");
                    if (specific) {
                        specificMatched = true;
                        (field.equals("disallow") ? specificDisallow : specificAllow).add(value);
                    }
                    if (wildcard) {
                        (field.equals("disallow") ? wildcardDisallow : wildcardAllow).add(value);
                    }
                }
                default -> {
                }
            }
        }
        if (specificMatched) {
            return new Rules(specificDisallow, specificAllow);
        }
        return new Rules(wildcardDisallow, wildcardAllow);
    }

    public static Rules fetch(Fetcher fetcher, URI baseUri) throws IOException {
        URI robotsUri = baseUri.resolve("/robots.txt");
        Optional<String> body = fetcher.getOptional(robotsUri);
        if (body.isEmpty()) {
            return Rules.ALLOW_ALL;
        }
        return rulesFor(body.get(), agentToken(Fetcher.USER_AGENT));
    }
}
