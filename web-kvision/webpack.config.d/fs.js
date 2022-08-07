const fallback = config.resolve.fallback || {};
Object.assign(fallback, {
        fs: false,
        path: false,
        crypto: false,
});
config.resolve.fallback = fallback;
