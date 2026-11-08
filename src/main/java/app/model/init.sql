DROP DATABASE IF EXISTS svg_temperature_db;
CREATE DATABASE svg_temperature_db;
USE svg_temperature_db;

--
CREATE TABLE temperature_records
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    input_value DOUBLE NOT NULL,
    input_unit VARCHAR(20) NOT NULL,
    output_value DOUBLE NOT NULL,
    output_unit VARCHAR(20) NOT NULL,
    converted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE translations
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    unit_code VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE unit_translation
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    unit_id BIGINT NOT NULL,
    lang VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    UNIQUE (unit_id, lang),
    CONSTRAINT fk_unit_translation_unit FOREIGN KEY (unit_id) REFERENCES translations(id)
);

INSERT INTO translations (unit_code) VALUES
    ('C'),
    ('F'),
    ('K');

INSERT INTO unit_translation (unit_id, lang, name, description)
VALUES
    ((SELECT id FROM translations WHERE unit_code = 'C'), 'en', 'Celsius', 'Temperature measured in degrees Celsius'),
    ((SELECT id FROM translations WHERE unit_code = 'C'), 'fi', 'Celsius', 'Lämpötila mitattuna celsiusasteina'),
    ((SELECT id FROM translations WHERE unit_code = 'C'), 'sv', 'Celsius', 'Temperatur mätt i grader Celsius'),
    ((SELECT id FROM translations WHERE unit_code = 'C'), 'ja', 'セルシウス', 'セルシウス度で測定された温度'),
    ((SELECT id FROM translations WHERE unit_code = 'C'), 'ar', 'سلسيوس', 'درجة الحرارة المقاسة بالدرجات المئوية'),
    ((SELECT id FROM translations WHERE unit_code = 'F'), 'en', 'Fahrenheit', 'Temperature measured in degrees Fahrenheit'),
    ((SELECT id FROM translations WHERE unit_code = 'F'), 'fi', 'Fahrenheit', 'Lämpötila mitattuna fahrenheitasteina'),
    ((SELECT id FROM translations WHERE unit_code = 'F'), 'sv', 'Fahrenheit', 'Temperatur mätt i grader Fahrenheit'),
    ((SELECT id FROM translations WHERE unit_code = 'F'), 'ja', 'ファーレンハイト', '華氏で測定された温度'),
    ((SELECT id FROM translations WHERE unit_code = 'F'), 'ar', 'فهرنهايت', 'درجة الحرارة المقاسة بالدرجات الفهرنهايتية'),
    ((SELECT id FROM translations WHERE unit_code = 'K'), 'en', 'Kelvin', 'Temperature measured in Kelvin'),
    ((SELECT id FROM translations WHERE unit_code = 'K'), 'fi', 'Kelvin', 'Lämpötila mitattuna kelvineinä'),
    ((SELECT id FROM translations WHERE unit_code = 'K'), 'sv', 'Kelvin', 'Temperatur mätt i kelvin'),
    ((SELECT id FROM translations WHERE unit_code = 'K'), 'ja', 'ケルビン', 'ケルビンで測定された温度'),
    ((SELECT id FROM translations WHERE unit_code = 'K'), 'ar', 'كلفن', 'درجة الحرارة المقاسة بالكلفن');