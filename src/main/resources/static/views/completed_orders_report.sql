CREATE OR REPLACE VIEW completed_orders_report AS
SELECT o.created_at                                                                                AS "Дата",
       u.user_surname || ' ' || LEFT(u.user_name, 1) || '.' || LEFT(u.user_patronymic, 1) || '.'   AS "Клиент",
       o.order_startpoint || ' → ' || o.order_endpoint                                             AS "Маршрут",
      'Фура ' || cr.car_brand                                                                      AS "Транспортное средство",
      d.user_surname || ' ' || LEFT(d.user_name, 1) || '.' || LEFT(d.user_patronymic, 1) || '.'    AS "Водитель",
      o.order_status                                                                               AS "Статус"
FROM "orders" o
JOIN "users" u ON o.user_id = u.user_id
JOIN car cr ON o.car_id = cr.car_id
JOIN "users" d ON cr.user_id = d.user_id
WHERE o.order_status = 'DELIVERED'
ORDER BY o.created_at;

SELECT * FROM completed_orders_report
WHERE "Дата" BETWEEN :start_date AND :end_date;