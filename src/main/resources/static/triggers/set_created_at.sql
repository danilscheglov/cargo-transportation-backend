CREATE OR REPLACE FUNCTION set_created_at() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.created_at IS NULL THEN
        NEW.created_at = CURRENT_DATE;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER trg_set_created_at
BEFORE
INSERT ON maintenance_request
FOR EACH ROW EXECUTE FUNCTION set_created_at();