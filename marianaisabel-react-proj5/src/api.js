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