CREATE OR REPLACE PROCEDURE complete_maintenance(p_maintenance_request_id INT) LANGUAGE PLPGSQL AS $$
DECLARE
    v_car_id INT;
BEGIN
    UPDATE maintenance_request
    SET maintenance_request_status = 'Завершена'
    WHERE maintenance_request_id = p_maintenance_request_id;

    SELECT car_id INTO v_car_id
    FROM maintenance_request
    WHERE maintenance_request_id = p_maintenance_request_id;

    UPDATE car
    SET car_condition = 'Исправно'
    WHERE car_id = v_car_id;

    COMMIT;
END;
$$;