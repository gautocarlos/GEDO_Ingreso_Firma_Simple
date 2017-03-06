Feature: GEDO_Ingreso_Firma_Simple

Scenario: Firmar tarea de firma simple con certificado de servidor, de tipo de producción libre.

Given El usuario cuenta cuanto mínimo una tarea de firma en su bandeja

When Ingresa a la URL "http://eug.nac.gde.gob.ar/gedo-web/"
When El usuario ingresa al sistema con sus credenciales: usuario "RUBIEE" y password "Zaq12w"
When El usuario firma una tarea de firma simple con certificado
Then Se genera un número de documento GDE