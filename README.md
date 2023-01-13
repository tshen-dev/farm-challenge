## About The Project

This is my challenge project to apply in Fram.

Thanks for your time to review it.

### Prerequisites

You need [Docker](https://www.docker.com/) to run application
 and using [Postman](https://www.postman.com/downloads/) to review the API

### Installation

1. Build the application image
   ```sh
   docker-compose build
   ```

2. Run the application
   ```sh
   docker-compose up
   ```
3. Import Postman collection from `Fram challenge.postman_collection.json` use the `app.authorization-key` in `application config` for authorization (default: `uwlkxjekfjvhjweylgxc`).

4. Stop and remove the application deployed
    ```sh
   docker-compose down
   ```

Hope it runs well on your machine (❁´◡`❁)