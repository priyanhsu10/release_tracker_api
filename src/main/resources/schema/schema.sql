-- EIM
-- ├── id, name, description
--
-- Component
-- ├── id, name, eim_id (FK), description
--
-- Environment
-- ├── id, name, component_id (FK)
--
-- Server
-- ├── id, hostname, ip_address, environment_id (FK), description
--
-- Release
-- ├── id, artifact_version, artifact_url, deployed_at, jira_ticket_id (FK),
-- │   component_id (FK), environment_id (FK), jenkins_job_id
--
-- ServerDeployment
-- ├── id, server_id (FK), release_id (FK), deployed_at


create table  if not EXISTS  tbl_eims (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);
create table  if not exists  tlb_components (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    eim_id INTEGER NOT NULL REFERENCES tbl_eims(id),
    description TEXT
);
create table  if not exists  tbl_environments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    component_id INTEGER NOT NULL REFERENCES tlb_components(id)
);
create table  if not exists tbl_servers (
    id SERIAL PRIMARY KEY,
    hostname VARCHAR(255) NOT NULL,
    ip_address VARCHAR(45) NOT NULL,
    environment_id INTEGER NOT NULL REFERENCES tbl_environments(id),
    description TEXT
);
create table  if not exists tbl_releases (
    id SERIAL PRIMARY KEY,
    artifact_version VARCHAR(255) NOT NULL,
    artifact_url TEXT NOT NULL,
    jira_ticket_id VARCHAR(255),
    component_id INTEGER NOT NULL REFERENCES tlb_components(id),
    environment_id INTEGER NOT NULL REFERENCES tbl_environments(id),
    jenkins_job_id VARCHAR(255),
    psid varchar(45) NOT NULL ,
    chg_number varchar(45),
    vulture_url TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    branch_url VARCHAR(500) NOT NULL,
    release_notes TEXT

);
create table if not exists tbl_server_deployments (
    id SERIAL PRIMARY KEY,
    server_id INTEGER NOT NULL REFERENCES tbl_servers(id),
    release_id INTEGER NOT NULL REFERENCES tbl_releases(id),
    deployed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);