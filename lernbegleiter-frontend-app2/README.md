# LernbegleiterFrontendApp2

## Development server

Run `ng serve --proxy-config proxy.conf.json --open` for a dev server.

The app will automatically reload if you change any of the source files.

The Backend application needs to be running for the app to work correctly.

## Code Generation / Angular CLI

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

Use something like `ng g s services/login-service --module=app.module` for quick creation with injector support.

## Build / Installation

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

You can run `mvn clean install -DskipTests -P install-npm` to prepare maven for building. (You only need this once)

Run `mvn clean install -DskipTests -P build-frontend` after preparing maven to build the angular project and copy it to the backend app.
