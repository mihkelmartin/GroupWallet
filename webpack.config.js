var path = require('path');


module.exports = {
    mode : 'none',
    entry: './src/main/js/app.js',
    devtool: 'sourcemaps',
    devServer: {
      port: 8080
    },
    cache: true,
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query: {
                    cacheDirectory: true,
                    presets: ['env', 'react', 'stage-0']
                }
            }
        ]
    }
};