-- Create the sec_users table with your specified structure and additional fields
CREATE TABLE "public"."sec_users" (
  -- Core fields
  "id" varchar(36) NOT NULL,
  "username" varchar(255) NOT NULL,
  "password" varchar(255) NOT NULL,
  "email" varchar(255) NOT NULL,
  "role" varchar(50),
  "status" varchar(50),

  -- Personal Information
  "first_name" varchar(100),
  "last_name" varchar(100),
  "phone_number" varchar(20),
  "profile_picture" varchar(255),
  "about_me" text,

  -- Address Information
  "address" text,
  "city" varchar(100),
  "state" varchar(100),
  "country" varchar(100),
  "pin_code" varchar(20),

  -- Audit fields
  "created_at" timestamp(6) NOT NULL,
  "created_by" varchar(36) NOT NULL,
  "updated_at" timestamp(6) NOT NULL,
  "updated_by" varchar(36) NOT NULL,

  -- Soft delete fields
  "is_deleted" boolean NOT NULL DEFAULT false,
  "deleted_at" timestamp(6),
  "deleted_by" varchar(36),

  -- Constraints
  CONSTRAINT "sec_users_pkey" PRIMARY KEY ("id"),
  CONSTRAINT "uk_sec_users_username" UNIQUE ("username"),
  CONSTRAINT "uk_sec_users_email" UNIQUE ("email"),
  CONSTRAINT "sec_users_role_check" CHECK (
    role = ANY (ARRAY['ADMIN', 'MANAGER', 'FREELANCER', 'USER']::varchar[])
  ),
  CONSTRAINT "sec_users_status_check" CHECK (
    status = ANY (ARRAY['ACTIVE', 'INACTIVE', 'DELETED', 'BLOCKED', 'SUSPENDED']::varchar[])
  )
);

-- Create indexes with exact specifications
CREATE INDEX "idx_users_username" ON "public"."sec_users" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "idx_users_email" ON "public"."sec_users" USING btree (
  "email" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "idx_users_role" ON "public"."sec_users" USING btree (
  "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "idx_users_status" ON "public"."sec_users" USING btree (
  "status" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- Add comments
COMMENT ON TABLE "public"."sec_users" IS 'Stores user account information and authentication details';
COMMENT ON COLUMN "public"."sec_users"."id" IS 'Primary key (UUID)';
COMMENT ON COLUMN "public"."sec_users"."username" IS 'Unique username for login';
COMMENT ON COLUMN "public"."sec_users"."email" IS 'User email address';
COMMENT ON COLUMN "public"."sec_users"."role" IS 'User role (ADMIN, MANAGER, FREELANCER, USER)';
COMMENT ON COLUMN "public"."sec_users"."status" IS 'Account status (ACTIVE, INACTIVE, etc.)';
COMMENT ON COLUMN "public"."sec_users"."is_deleted" IS 'Soft delete flag';

-- Set table ownership
ALTER TABLE "public"."sec_users" OWNER TO "postgres";

--ADMIN Password -> Admin12345
--USER Password -> User12345
