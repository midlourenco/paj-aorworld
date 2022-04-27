import React from "react";
import { render } from "react-dom";
import { Button, Snowflakes, Center } from "./components";
import useFetch from 'use-http';
import "../styles.css";

/**
 * File with scripts regarding API requests
 */

 const rootPath = "http://localhost:8080/marianaisabel-backend-proj5/rest";
let token = "";

//https://use-http.com/#/?id=options



//exemplo do fetch usado no trabalho proj4 
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