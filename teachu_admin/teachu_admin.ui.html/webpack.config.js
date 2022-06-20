const baseConfig = require('@eclipse-scout/cli/scripts/webpack-defaults');

module.exports = (env, args) => {
  args.resDirArray = ['src/main/resources/WebContent', 'node_modules/@eclipse-scout/core/res'];
  const config = baseConfig(env, args);

  config.entry = {
    'teachu_admin': './src/main/js/teachu_admin.js',
    'login': './src/main/js/login.js',
    'logout': './src/main/js/logout.js',
    'teachu_admin-theme': './src/main/js/teachu_admin-theme.less',
    'teachu_admin-theme-dark': './src/main/js/teachu_admin-theme-dark.less'
  };

  return config;
};
