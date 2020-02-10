module.exports = function(config) {
  config.set({
    basePath: "",
    frameworks: ["jasmine", "@angular/cli"],
    plugins: [
      require("karma-mocha-reporter"),
      require("karma-jasmine"),
      require("karma-chrome-launcher"),
      require("karma-coverage-istanbul-reporter"),
      require("karma-junit-reporter"),
      require("@angular/cli/plugins/karma")
    ],
    // list of files to exclude
    exclude: ["node_modules"],
    client: {
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    files: [{ pattern: "./src/test.ts", watched: false }],
    preprocessors: {
      "./src/**/*.ts": ["@angular/cli"]
    },
    mime: {
      "text/x-typescript": ["ts", "tsx"]
    },
    coverageIstanbulReporter: {
      reports: ["html"],
      fixWebpackSourcePaths: true
    },
    angularCli: {
      environment: "dev"
    },
    reporters: ["progress", "mocha", "dots", "junit"],
    port: 9876,
    colors: true,
    logLevel: config.LOG_ERROR,
    autoWatch: true,
    browsers: ["Chrome"],
    singleRun: true,
    browserNoActivityTimeout: 20000,
    junitReporter: {
      outputFile: "./client-test-results.xml"
    }
  });
};
