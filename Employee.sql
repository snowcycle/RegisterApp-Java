CREATE TABLE employee (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  employeeid int NOT NULL DEFAULT(0),
  firstname character varying(128) NOT NULL DEFAULT(''),
  lastname character varying(128) NOT NULL DEFAULT(''),
  password bytea NOT NULL DEFAULT(''),
  active boolean NOT NULL DEFAULT(FALSE), 
  classification int NOT NULL DEFAULT(0),
  managerid uuid NOT NULL DEFAULT CAST('00000000-0000-0000-0000-000000000000' AS uuid),
  createdon timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT employee_pkey PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

--  A cheap way to prevent malicious actors from using employee IDs to determine
-- how many employees are in your company. Obviously this is not foolproof.
CREATE SEQUENCE employee_employeeid_seq AS int START WITH 253 INCREMENT BY 7 OWNED BY employee.employeeid;

ALTER TABLE employee ALTER employeeid SET DEFAULT nextval('employee_employeeid_seq');

CREATE INDEX ix_employee_employeeid
  ON employee
  USING btree(employeeid);

-----
CREATE TABLE activeuser (
  id uuid NOT NULL DEFAULT gen_random_uuid(),
  employeeid uuid NOT NULL,
  name character varying(256) NOT NULL DEFAULT(''),
  classification int NOT NULL DEFAULT(0),
  sessionkey character varying (128) NOT NULL DEFAULT(''),
  createdon timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT activeuser_pkey PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

CREATE INDEX ix_activeuser_employeeid
  ON activeuser
  USING btree(employeeid);

CREATE INDEX ix_activeuser_sessionkey
  ON activeuser
  USING btree(sessionkey);

