
/**
 * função que devolve informção do token da pessoa logada
 * @returns 
 */
 export function getAuthString(){
    // return  "49f4110f-4f24-450a-bab7-aae9775e98b4";
   return localStorage.getItem("Authorization");
 }
 
 
 /**
  * função que devolve informção do user ID da pessoa logada
  * @returns 
  */
 export function getMyUserId(){
   //  return  "admin";
       return localStorage.getItem("userId");
 }
   