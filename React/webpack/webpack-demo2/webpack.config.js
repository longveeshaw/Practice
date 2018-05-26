var path = require("path");
var UglifyJsPlugin = require('uglifyjs-webpack-plugin')
module.exports = {
    entry: {
        admin: "./admin/index.js",
        consumer: "./consumer/index.js"

    },
    output: {
        path: path.join(__dirname, "dist"),
        filename: "[name].bundle.js",
        publicPath: "/dist/"
    },
    plugins: [
        new UglifyJsPlugin()
    ],
    module: {
        noParse: [/jquery/],
        loaders: [
            {
                test: /\.js$/,
                loader: "babel-loader"

            }, {
                test: /\\icons\\/,
                loader: "url-loader"

            }, {
                test: /\\images\\/,
                loader: "file-loader"
            }, {
                test: /node_modules/,
                loader: "babel-loader"
            }
        ]
    }


};