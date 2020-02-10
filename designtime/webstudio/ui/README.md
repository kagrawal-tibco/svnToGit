## Notice

All commands mentioned in this readme shall be executed inside the top level of `ams-web` directory.

## Install Node and NPM

## Install angular-cli globally

Run `npm install -g @angular/cli`

## Install Dependencies

1. Run `rm -rf ./node_modules` to remove existing `node_modules`.
2. Run `npm install` install dependencies.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

Run `ng serve -pc proxy.conf.json` for a dev server that proxies API requests based on `proxy.conf.json`.

Tip: Run `./node_modules/@angular/cli/bin/ng` if you do not have `angular-cli` installed globally.

## Build

1. Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.
2. Use the `-prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).
Run `ng test --code-coverage` to execute the unit tests via [Karma](https://karma-runner.github.io) and generate a coverage report.

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).
Before running the tests make sure you are serving the app via `ng serve`.

## Pre Commit Hooks

1. Linting

The AMS uses TSLint (https://palantir.github.io/tslint/) as a pre-commit hook. The rules are defined in tslint.json.

Additionally, custom rules are provided by http://codelyzer.com/rules/

It is the responsibility of the developer to integrate the rules with your IDE of choice.

If the staged files that you are attempting to commit have code style violations, you will not be allowed to commit until they have been resolved.

This was introduced on 10/25/17. To track success, we will periodically check how many tslinting errors there are by counting the lines returned by
`npm run lint | grep ERROR -c`

Progress:
10/25/17: 4605
12/19/17: 2159

2. Compilation

The AMS git repository has some built in preventative measures to aid the development process.

When making changes to the AMS client, the pre-push commit hook will attempt to compile the client.

This provides an easy way to keep the repository in a working state and will prevent silly mistakes.

3. Skipping

If a commit must be made and it is necessary to circumvent these requirements, this is provided by adding the --no-verify flag to the commit.
Thus, the command would be `git commit -m "SB-${ticket-number}: ${descriptive comment describing commit}" --no-validate`
