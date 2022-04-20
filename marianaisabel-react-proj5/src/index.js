import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';

import reportWebVitals from './reportWebVitals';

import { Provider } from 'react-redux';
import store from './redux/store'

import { ChakraProvider } from "@chakra-ui/react";
import customTheme from "./theme.js";


const rootElement = document.getElementById("root");

ReactDOM.render(
  <ChakraProvider theme={customTheme}>
    <Provider store={store}>
      <App />
    </Provider>
  </ChakraProvider>, rootElement
);
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
