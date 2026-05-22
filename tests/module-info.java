/**
 * @release 25
 * @test build.jenesis.modules
 * @pin org.junit.jupiter 5.11.3 SHA-256/ac7578efed162367c3ddc006338e07d4571510fd9866642ea93d5b9e4ed2f665
 * @pin org.junit.jupiter.api 5.11.3 SHA-256/5d8147a60f49453973e250ed68701b7ff055964fe2462fc2cb1ec1d6d44889ba
 * @pin org.junit.jupiter.params 5.11.3 SHA-256/0f798ebec744c4e6605fd4f2072f41a8e989e2d469e21db5aa67cf799c0b51ec
 * @pin org.junit.jupiter.engine 5.11.3 SHA-256/e62420c99f7c0d59a2159a2ef63e61877e9c80bd722c03ca8bf3bdcea050a589
 * @pin org.junit.platform.commons 1.11.3 SHA-256/be262964b0b6b48de977c61d4f931df8cf61e80e750cc3f3a0a39cdd21c1008c
 * @pin org.junit.platform.engine 1.11.3 SHA-256/0043f72f611664735da8dc9a308bf12ecd2236b05339351c4741edb4d8fab0da
 * @pin org.junit.platform.launcher 1.11.4 SHA-256/d7430bd029e7fcced53ee445e4d2d1a8a1e043ea4c4df43b6335a857f79761ae
 * @pin org.opentest4j 1.3.0 SHA-256/48e2df636cab6563ced64dcdff8abb2355627cb236ef0bf37598682ddf742f1b
 * @pin org.apiguardian.api 1.1.2 SHA-256/b509448ac506d607319f182537f0b35d71007582ec741832a1f111e5b5b70b38
 * @pin org.assertj.core 3.27.0 SHA-256/0b4d14008475fb362c2db090bc89c41b864d870216ccf8e8188fb60eb112ad68
 */
open module build.jenesis.modules.test {

    requires build.jenesis.modules;
    requires org.junit.jupiter;
    requires org.assertj.core;
}
