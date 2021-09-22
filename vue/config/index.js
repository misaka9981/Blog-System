'use strict'
  
const path = require('path')

module.exports = {
  dev: {
         assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      '/api': {
        target: 'http:         changeOrigin: true,           pathRewrite: {"^/api" : "/api"}
      }
    },
         host: '127.0.0.1',      port: 8888,      autoOpenBrowser: true,
    errorOverlay: true,
    notifyOnErrors: false,
    poll: false,  
                   useEslint: true,
              showEslintErrorsInOverlay: false,

     

         devtool: 'cheap-source-map',

                             cssSourceMap: false
  },

  build: {
         index: path.resolve(__dirname, '../dist/admin/index.html'),

         assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'admin/static',

     
    assetsPublicPath: '/',

     

    productionSourceMap: false,
         devtool: 'source-map',

                        productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

                        bundleAnalyzerReport: process.env.npm_config_report || false,

         generateAnalyzerReport: process.env.npm_config_generate_report || false
  }
}
