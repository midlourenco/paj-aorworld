import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';

import reportWebVitals from './reportWebVitals';

import { Provider } from 'react-redux';
import store from './redux/store'

import { ChakraProvider } from "@chakra-ui/react";
import customTheme from "./theme.js";

// import messages from './translations';
// import {IntlProvider, FormattedMessage} from "react-intl";

const rootElement = document.getElementById("root");
// export const myThemeProvider = ({ children }) => {
//   return <ChakraProvider>{children}</ChakraProvider>;
// };



ReactDOM.render(
  <Provider store={store}>
      <ChakraProvider theme={customTheme}>
        <App />
      </ChakraProvider>
  </Provider>, rootElement
);
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
