import React from "react";
import { render } from "react-dom";
import useFetch from 'use-http';
import{getAuthString} from "./auth"
import config from "./config";

const ROOT_PATH_WS =config.WS_URL;
var wsocket; 

//Tal como no proj4:
//ENVIAR TOKEN PARA O SERVIDOR PARA VALIDAR QUE PODE TER ACESSO AO CANAL:
//em vez desta abordagem onde estamos  a enviar o token por path param, vimos como alternativa envia-la:
//1 gerar 1 token temporario no backend para esta situação, que assim que se estabelecesse o connect era eliminado - envolvia mudar alguma logica no backend(guardar token temporario, etc)
//2 passa-lo pelo campo protocolo do websocket, e assim, não ficaria exposto no endereço - não se encontrou forma este campo no backend
//https://stackoverflow.com/questions/4361173/http-headers-in-websockets-client-api
//3 enviar por send e ter onMessage do lado do servidor 
/**
 * funçao que estabelecesse ligaçao ao canal websocket 
 * @param {} onMessage recebe uma função que trata da informação que é enviada pelo servidor
 * @returns  o próprio websocket
 */
export function connectWSGeneralDashboard(onMessage) {
    let authString= getAuthString();

	wsocket = new WebSocket(ROOT_PATH_WS + "generalStatsWS/" + authString);
    console.log("criar o websocket to WS general info")
	wsocket.onmessage = onMessage;

    return wsocket;
}

// /**
//  * funçao que estabelecesse ligaçao ao canal websocket 
//  *  @param {} username username do qual se quer visualizar informação 
//  * @param {} onMessage recebe uma função que trata da informação que é enviada pelo servidor
//  * @returns  o próprio websocket
//  */
// export function connectWSSpecificInfoDashboard(username, onMessage) {
//     let authString= getAuthString();
//     wsocket = new WebSocket(ROOT_PATH_WS+ "individualStatsWS/" +username+"/"+ authString);
//     console.log("criar o websocket to WS specific info")
// 	wsocket.onmessage = onMessage;
//    // console.log(wsocket.onmessage );
//     return wsocket;
// }




/**
 * File with scripts regarding API requests
 */

const rootPath = config.API_URL;
let authString= getAuthString();


//Usando o hook do useFetch, os métods estão feitos directamente no sítio que recebe a informação
//https://use-http.com/#/?id=options



//exemplo do fetch usado até ao trabalho proj4 
//////////////////////////////////////////////////////////////
//R5 - FUNÇÃO para GET USER
//////////////////////////////////////////////////////////////
// /**
//  * Função que faz get de um user através do username
//  */
//  async function getUserByUsernameAPI(username, authString, onSucess, onError) {
// 	return await fetch(rootPath + username, {
// 		method: "GET",
// 		headers: {
// 			Authorization: authString,
// 			//'Authorization': "22343",
// 			Accept: "application/json",
// 			"Content-Type": "application/json",
// 		},
// 	})
// 		.then(function (response) {
// 			console.log("response do get vai ser validada");
// 			validateResponse(response); //se for not ok, faz skip a todos os then que vêm a seguir e faz trigger ao catch
// 			return response.json();
// 		})
// 		.then(function (data) {
// 			console.debug("response do get traz o utilizador");
// 			onSucess(data, authString);
// 		})
// 		.catch(function (error) {
// 			console.log("houve um problema no get user: " + error);
// 			onError(error);
// 		});
//}