# Cuenta Service
### Este el servicio que maneja la entidad de Cuenta.

## Endpoints:

### Base url: http://localhost:8080/Cuenta
- #### Cuenta(POST): 
     #### Registra una cuenta en el sistema.  
  {  
    &nbsp; "userId" : 1,  
    &nbsp; "mercadoPago" : 40  
  }
- #### Cuenta(GET):
   Devuelve todas las cuentas registradas en el sistema. 
  
- #### Cuenta/{IdCuenta}(GET):  
   Devuelve la cuenta(si existe) que contenga el id de IdCuenta.

- #### Cuenta{IdCuenta}(PUT):
  Actualiza la cuenta que tenga el id IdCuenta con los parametros solicitados.  
   {  
     &nbsp; "userId" : 1,  
     &nbsp; "mercadoPago" : 40  
   }

- #### Cuenta{IdCuenta}/deshabilitar(PUT):
  Deshabilita la cuenta(Solo se va a actualizar con un token que sea ADMIN).

- #### Cuenta{IdCuenta}/habilitar(PUT):
  Habilita nuevamente la cuenta(Solo se va a actualizar con un token que sea ADMIN).

- #### Cuenta{IdCuenta}(DELETE):
  Borra la cuenta (si existe) con la IdCuenta colocada.
