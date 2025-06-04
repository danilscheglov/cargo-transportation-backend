CREATE OR REPLACE VIEW fleet_technical_status AS
SELECT c.car_number                                       AS "Номер транспортного средства",
       c.car_brand || ' ' || c.car_model                  AS "Марка и модель",
       c.car_capacity                                     AS "Пробег (км)",
       TO_CHAR(c.car_last_maintenance_date, 'DD.MM.YYYY') AS "Дата последнего обслуживания",
       c.car_condition                                    AS "Состояние"
FROM car c
ORDER BY c.car_condition, c.car_capacity DESC;

SELECT * FROM fleet_technical_status
WHERE "Пробег (км)" BETWEEN :min_mileage AND :max_mileage AND (:condition_filter IS NULL OR "Состояние" = :condition_filter);
