```
erDiagram

Gebiet ||--o{ stadt_typ : "g_typ"
Gebiet ||--o{ fluss : "fluss"
Gebiet }o--|| Gebiet : "hauptstadt_von"
fluss ||--o{ fließt_durch : "f_id"
Gebiet ||--o{ fließt_durch : "g_id"
Gebiet ||--o{ grenzt_an : "A_g_id"
Gebiet ||--o{ grenzt_an : "B_g_id"
Sprache ||--o{ wird_gesprochen_in : "s_id"
Gebiet ||--o{ wird_gesprochen_in : "g_id"

Gebiet {
int id PK
varchar(200) name
bigint einwohnerzahl
bool hauptstadt
int hauptstadt_von FK
int fluss FK
varchar(100) g_typ FK
}

stadt_typ {
int id PK
varchar(200) typ
}

fluss {
int id PK
varchar(200) name
bigint laenge
    }

fließt_durch {
int id PK
int f_id FK
int g_id FK
}

grenzt_an {
int id PK
int A_g_id FK
int B_g_id FK
}

Sprache {
int id PK
varchar(200) typ
}

wird_gesprochen_in {
int id PK
int s_id FK
int g_id FK
}
