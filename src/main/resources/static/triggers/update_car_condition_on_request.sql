CREATE OR REPLACE FUNCTION update_car_condition_on_request() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.maintenance_request_status = 'В процессе' THEN
        UPDATE car
        SET car_condition = 'На обслуживании'
        WHERE car_id = NEW.car_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER trg_update_car_condition_on_request AFTER
INSERT ON maintenance_request
FOR EACH ROW EXECUTE FUNCTION update_car_condition_on_request();