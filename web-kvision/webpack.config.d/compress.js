const CompressionPlugin = require("compression-webpack-plugin");
const zlib = require("zlib");

config.plugins.push(new CompressionPlugin({
    filename: "[path][base].br",
    algorithm: "brotliCompress",
    test: /\.(js|css|html|svg|gif|png|wasm)$/,
    compressionOptions: {
        params: {
            [zlib.constants.BROTLI_PARAM_QUALITY]: 11,
        },
        threshold: 10240,
        minRatio: 0.8,
        deleteOriginalAssets: true,
    }
}))