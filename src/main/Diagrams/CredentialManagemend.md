# Credential Management

The following diagramms were maid in Intellij with the mermaid Plugin


````mermaid
    classDiagram
    CredentialManager : - PosUser[] users
    CredentialManager : - PosUser selected
    CredentialManager : + static get_username() String
    CredentialManager : + static get_password() String
    CredentialManager : + static set_credentials() boolean
    CredentialManager : - load_users() PosUser[]
    CredentialManager : - save_users() boolean
    
    CredentialManager o-- PosUser : handles
    
    PosUser : - String username
    PosUser : - String password
    PosUser : - String Name
    PosUser : - String description
    PosUser : - Image profile_picture
    PosUser : + equlas() boolean

````

CredentialManager will be a Singleton