CREATE TABLE "public"."sec_users" (
  "id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,

  "username" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "email" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "role" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",

  "about_me" varchar(255) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "city" varchar(255) COLLATE "pg_catalog"."default",
  "country" varchar(255) COLLATE "pg_catalog"."default",
  "first_name" varchar(255) COLLATE "pg_catalog"."default",
  "last_name" varchar(255) COLLATE "pg_catalog"."default",
  "phone_number" varchar(255) COLLATE "pg_catalog"."default",
  "pin_code" varchar(255) COLLATE "pg_catalog"."default",
  "profile_picture" varchar(255) COLLATE "pg_catalog"."default",
  "state" varchar(255) COLLATE "pg_catalog"."default",


  "is_deleted" bool NOT NULL DEFAULT false,
  "deleted_at" timestamp(6),
  "deleted_by" varchar(36) COLLATE "pg_catalog"."default",

  "created_at" timestamp(6) NOT NULL,
  "created_by" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,

  "updated_at" timestamp(6) NOT NULL,
  "updated_by" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,


  CONSTRAINT "sec_users_pkey" PRIMARY KEY ("id"),
  CONSTRAINT "idx_users_username" UNIQUE ("username"),
  CONSTRAINT "idx_users_email" UNIQUE ("email"),
  CONSTRAINT "sec_users_role_check" CHECK (role::text = ANY (ARRAY['ADMIN'::character varying, 'CLIENT'::character varying, 'FREELANCER'::character varying]::text[])),
  CONSTRAINT "sec_users_status_check" CHECK (status::text = ANY (ARRAY['ACTIVE'::character varying, 'INACTIVE'::character varying]::text[]))
)
;

ALTER TABLE "public"."sec_users"
  OWNER TO "postgres";

CREATE INDEX "idx_users_role" ON "public"."sec_users" USING btree (
  "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "idx_users_status" ON "public"."sec_users" USING btree (
  "status" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

--ADMIN Password -> Admin12345
--USER Password -> User12345
INSERT INTO "public"."sec_users" ("id", "username", "email", "password", "role", "status", "about_me", "address", "city", "country", "first_name", "last_name", "phone_number", "pin_code", "profile_picture", "state", "is_deleted", "deleted_at", "deleted_by", "created_at", "created_by", "updated_at", "updated_by") VALUES ('15dedb82-3983-4a16-aed8-eb6ca030dd17', 'wserp-shankar', 'Shankar.wserp@yopmail.com', '$2a$10$a8DU0vO9T1Ot33Uf3Y1cl./XdH6ogyWKIScDrXgkzn0cuG1eSPcKS', 'CLIENT', 'ACTIVE', 'I am Majdur majbur', 'Nalime', 'Chhapra', 'India', 'Shankar', 'Yadav', '9999955555', '200002', 'https://i.postimg.cc/Lhd0nWK7/image.jpg', 'Bihar', 'f', NULL, NULL, '2025-09-18 17:26:27.971629', '00000000-0000-0000-0000-000000000000', '2025-09-18 17:51:26.665207', '00000000-0000-0000-0000-000000000000');
INSERT INTO "public"."sec_users" ("id", "username", "email", "password", "role", "status", "about_me", "address", "city", "country", "first_name", "last_name", "phone_number", "pin_code", "profile_picture", "state", "is_deleted", "deleted_at", "deleted_by", "created_at", "created_by", "updated_at", "updated_by") VALUES ('57ccc61f-5eaa-4bba-9358-2dc01fc91d4c', 'wserp-ujjwal', 'ujjwal.wserp@yopmail.com', '$2a$10$a8DU0vO9T1Ot33Uf3Y1cl./XdH6ogyWKIScDrXgkzn0cuG1eSPcKS', 'FREELANCER', 'ACTIVE', 'I am Majdur majbur', 'Theke ke pass', 'Bagpat', 'India', 'Ujjwal', 'Thakur', '9999955558', '122121', 'https://i.postimg.cc/CxH0jGKX/pug-dog-isolated-white-background-2829-11416.avif', 'UP', 'f', NULL, NULL, '2025-09-18 17:28:32.156128', '00000000-0000-0000-0000-000000000000', '2025-09-18 18:02:13.315048', '00000000-0000-0000-0000-000000000000');
INSERT INTO "public"."sec_users" ("id", "username", "email", "password", "role", "status", "about_me", "address", "city", "country", "first_name", "last_name", "phone_number", "pin_code", "profile_picture", "state", "is_deleted", "deleted_at", "deleted_by", "created_at", "created_by", "updated_at", "updated_by") VALUES ('f2e23ab6-5cb6-47f9-9ce2-f43e61a23f97', 'wserp-akram', 'arkram.wserp@yopmail.com', '$2a$10$a8DU0vO9T1Ot33Uf3Y1cl./XdH6ogyWKIScDrXgkzn0cuG1eSPcKS', 'FREELANCER', 'ACTIVE', 'I am Majdur majbur', 'Madanpur Khadar', 'New Delhi', 'India', 'Akram', 'ERP', '9999955559', '110076', 'https://i.postimg.cc/cCZM8GkV/Labrador-Retriever-portrait.jpg', 'Delhi', 'f', NULL, NULL, '2025-09-18 17:29:35.657489', '00000000-0000-0000-0000-000000000000', '2025-09-18 18:03:21.561816', '00000000-0000-0000-0000-000000000000');
INSERT INTO "public"."sec_users" ("id", "username", "email", "password", "role", "status", "about_me", "address", "city", "country", "first_name", "last_name", "phone_number", "pin_code", "profile_picture", "state", "is_deleted", "deleted_at", "deleted_by", "created_at", "created_by", "updated_at", "updated_by") VALUES ('14fa9624-1c9a-4b18-8f0b-9c46649c59ec', 'wserp-azad', 'azad.wserp@yopmail.com', '$2a$10$a8DU0vO9T1Ot33Uf3Y1cl./XdH6ogyWKIScDrXgkzn0cuG1eSPcKS', 'CLIENT', 'ACTIVE', 'I am Majdur majbur', 'Madanpur Khadar', 'New Delhi', 'India', 'Azad', 'ERP', '9999955557', '110076', 'https://i.postimg.cc/cCZM8GkV/Labrador-Retriever-portrait.jpg', 'Delhi', 'f', NULL, NULL, '2025-09-18 17:23:58.275223', '00000000-0000-0000-0000-000000000000', '2025-09-18 17:59:27.064421', '00000000-0000-0000-0000-000000000000');
INSERT INTO "public"."sec_users" ("id", "username", "email", "password", "role", "status", "about_me", "address", "city", "country", "first_name", "last_name", "phone_number", "pin_code", "profile_picture", "state", "is_deleted", "deleted_at", "deleted_by", "created_at", "created_by", "updated_at", "updated_by") VALUES ('7c3ae95e-d130-40ec-a562-c3f72156047c', 'wserp-akshay', 'akshay.wserp@yopmail.com', '$2a$10$a8DU0vO9T1Ot33Uf3Y1cl./XdH6ogyWKIScDrXgkzn0cuG1eSPcKS', 'FREELANCER', 'ACTIVE', 'I am Majdur majbur', 'dharavi', 'Mumbai', 'India', 'Akshay', 'Kumar', '9999955550', '123141', 'https://i.postimg.cc/7hDdPc3L/file-20241010-15-95v3ha.avif', 'Maharashtra', 'f', NULL, NULL, '2025-09-18 17:35:24.67371', '00000000-0000-0000-0000-000000000000', '2025-09-18 18:05:53.523524', '00000000-0000-0000-0000-000000000000');
INSERT INTO "public"."sec_users" ("id", "username", "email", "password", "role", "status", "about_me", "address", "city", "country", "first_name", "last_name", "phone_number", "pin_code", "profile_picture", "state", "is_deleted", "deleted_at", "deleted_by", "created_at", "created_by", "updated_at", "updated_by") VALUES ('426474d2-6d70-4c73-a51f-0d2857fc7953', 'wserp-sudhir', 'sudhir.wserp@yopmail.com', '$2a$10$a8DU0vO9T1Ot33Uf3Y1cl./XdH6ogyWKIScDrXgkzn0cuG1eSPcKS', 'FREELANCER', 'ACTIVE', 'I am Majdur majbur', 'Dudh wali gali', 'Panipath', 'India', 'Sudhir', 'Saini', '9999955551', '123445', 'https://i.postimg.cc/28tG2M1B/happy-puppy-welsh-corgi-14-600nw-2270841247.webp', 'Harayana', 'f', NULL, NULL, '2025-09-18 17:31:11.227879', '00000000-0000-0000-0000-000000000000', '2025-09-18 18:08:08.59154', '00000000-0000-0000-0000-000000000000');
INSERT INTO "public"."sec_users" ("id", "username", "email", "password", "role", "status", "about_me", "address", "city", "country", "first_name", "last_name", "phone_number", "pin_code", "profile_picture", "state", "is_deleted", "deleted_at", "deleted_by", "created_at", "created_by", "updated_at", "updated_by") VALUES ('c0a80108-9969-1f28-8199-696319fc0000', 'wserp-superadmin', 'aadmin.wserp@yopmail.com', '$2a$10$QwPtEDCpb8/M2mvriNy3VeAg6kCG6BmsEa7Tnxgrj5diQZsvER3la', 'ADMIN', 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'f', NULL, NULL, '2025-09-21 04:38:46.477968', '00000000-0000-0000-0000-000000000000', '2025-09-21 04:38:46.477968', '00000000-0000-0000-0000-000000000000');
INSERT INTO "public"."sec_users" ("id", "username", "email", "password", "role", "status", "about_me", "address", "city", "country", "first_name", "last_name", "phone_number", "pin_code", "profile_picture", "state", "is_deleted", "deleted_at", "deleted_by", "created_at", "created_by", "updated_at", "updated_by") VALUES ('667d5d8a-69b3-47f3-ac0f-6b508cc12f44', 'wserp-admin', 'admin.wserp@yopmail.com', '$2a$10$QwPtEDCpb8/M2mvriNy3VeAg6kCG6BmsEa7Tnxgrj5diQZsvER3la', 'ADMIN', 'ACTIVE', 'I am Majdur majbur', 'Madanpur Khadar', 'New Delhi', 'India', 'Admin', 'ERP', '9999955556', '110076', 'https://i.postimg.cc/cCZM8GkV/Labrador-Retriever-portrait.jpg', 'Delhi', 'f', NULL, NULL, '2025-09-18 17:20:07.249705', '00000000-0000-0000-0000-000000000000', '2025-09-18 17:56:59.727267', '00000000-0000-0000-0000-000000000000');

