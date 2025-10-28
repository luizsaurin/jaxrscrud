# Environment setup

Necessary steps to setup you machine to be able to run this application.

## Requirements

- Java JDK 21
- Apache Maven 3.9.9 

## WildFly

Download the latest stable version. https://www.wildfly.org/downloads/

Unzip the downloaded file into your directory of choice.

From the root directory of your wildfly installation, navigate to `/bin` and run the `standalone.bat` file.

If your installation is correct, it should initialize without any problems.

## Admin Console

To be able to apply change the server configurations, you should create a new super user for you.

From the root directory of your wildfly installation, navigate to `/bin` and run the `add-user.bat` file.

You will be prompted with some questions.

>What type of user do you wish to add? \
> a) Management User (mgmt-users.properties) \
> b) Application User (application-users.properties)

Choose option `a`

> Enter the details of the new user to add \
> Username :

Type your desired username

> Password recommendations are listed below. To modify these restrictions edit the add-user.properties > configuration file. \
>  - The password should be different from the username
>  - The password should not be one of the following restricted values {root, admin, administrator}
>  - The password should contain at least 8 characters, 1 alphabetic character(s), 1 digit(s), 1 > non-alphanumeric symbol(s) \
>
> Password :

Type your desired password. For local development, any password should be fine, even if does not follow all the recommendations above. For production environments, choose a strong password.

> Are you sure you want to use the password entered yes/no?

Choose option `yes`

> Re-enter Password :

Confirm your password

> What groups do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[  ]:

Enter roles `[root, admin, administrator]`

> About to add user 'your-user' for realm 'ManagementRealm'
> Is this correct yes/no?

Choose option `yes`

After this, you should have a valid super user. Access the admin console on `http://localhost:9990`. Enter your credentials and you should have access to the server configurations.

## Configure datasource

It is required to configure the datasource for the postgres database.

### PostgreSQL JDBC Driver

Download the latest stable version of the PostgreSQL JDBC Driver from the maven repository. https://mvnrepository.com/artifact/org.postgresql/postgresql.

Add the .jar to wilfdly modules, inside your wildfly instalaltion directory.

`/modules/system/layers/base/org/postgresql/main
`

If this directory does not exists, create it and place the .jar inside it.

### Module configuration

Inside the same directory mentioned previously, create a `module.xml` if it not exists. It's content should be:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.9" name="org.postgresql">
    <resources>
        <resource-root path="postgresql-42.7.3.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
```

The `path` attribute in the `<resource-root>` tag should be the exact name of the .jar driver file downloaded in the previous steps, in this case it's `postgresql-42.7.3.jar`.

At this point, your directory should look like this:

```
modules/
└── system/
    └── layers/
        └── base/
            └── org/
                └── postgresql/
            		└── main/
                		├── module.xml
						└── postgresql-42.7.8.jar
```

### Register driver

Edit the configuration file: `../standalone/configuration/standalone.xml`. Inside the `<datasources>` section, add this under `<drivers>`:

```xml
<driver name="postgresql" module="org.postgresql">
	<driver-class>org.postgresql.Driver</driver-class>
	<xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
</driver>
```

### Add datasource

Run the server. Go to `http://localhost:9990`. Login with your user.

Now when you go to Configuration → Subsystems → Datasources & Drivers → JDBC Drivers,
you should see postgresql listed.

Since this application uses Postgres database, there is a `docker-compose.yml` provided in the root directory of this project. Run it and create the database,

```sql
CREATE DATABASE productdb;
```

Now, go to Configuration → Subsystems → Datasources & Drivers → Datasources and create a new datasource. Follow the setup steps shown by the setup wizard.


1. Choose Template
	- Check `PostgeSQL` option
1. Attributes
	- Name: `PostgresDS`
	- JNDI Name: `java:/jdbc/PostgresDS`
1. JDBC Driver
	- Driver Name: open the combobox menu and select `postgresql`
	- Driver Class Name: `org.postgresql.Driver`
1. Connection
	- Connection URL: `jdbc:postgresql://localhost:5432/productdb`
	- User Name: `postgres`
	- Password: `postgres`

From now on, test the connection. If successful, your environment is ready to run the application. Check the instructions on README.md.