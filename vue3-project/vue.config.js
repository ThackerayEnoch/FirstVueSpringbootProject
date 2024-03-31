
module.exports = {
  devServer: {
    port: 80,
    proxy:{
    '/api': {
      target: 'https://dokidokiujn.top:8080',
      changeOrigin: true,
      pathRewrite: {
        '/api': ''
      }
    }
  }
  },

};
