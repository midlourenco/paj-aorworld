import React from "react";
import './style.css';
//In react-router-dom v6, "Switch" is replaced by routes "Routes".
//https://stackoverflow.com/questions/63124161/attempted-import-error-switch-is-not-exported-from-react-router-dom
import { 
    BrowserRouter as Router,
    Route, 
    Routes,
    Link,
    useParams
} from 'react-router-dom';
import { useState } from "react";
import {
  ChakraProvider,
  Flex,
  Text,
  Link as ChakraLink,
  Box,
  Grid,
  Image,
  HStack,
  Select,
  Button,
  Spacer,
  StackDivider
} from "@chakra-ui/react";
import messages from './translations'
import {IntlProvider, FormattedMessage} from "react-intl";

import Home from './pages/Home';
import Login from './pages/Login';
import News from './pages/News';
import NewsElem from "./pages/NewsElem";
import Projects from './pages/Projects';
import Project from './pages/Projects';
import AboutUs from './pages/AboutUs';
import Header from "./components/sections/Header";
import Register from "./pages/Register";
import ResetPassword from "./pages/ResetPassword";
import Footer from "./components/sections/Footer";


import { connect } from "react-redux";

//se dentro da pasta pages eu colocar um index com o export das outras páginas devo poder fazer o seguinte:
// import {Home, Login, News, Projects, AboutUs} from './pages'


/**
 * Para o provider IntlProvider ter acesso ao locale, vamos usar o redux para ir bucar esta informação 
 */

function App({language = "en",...props}) {
 
  console.log("language in app " + language)
  const locale=language;
  return (
    <IntlProvider locale={locale} messages ={messages[locale]}>
      <Box>
        <Router >
          <div className="App">
            <Header />

            <Routes>
                <Route path= "/" exact element ={<Home />} />
                <Route path= "/login" element ={<Login />}/>
                <Route path= "/reset_password"  element ={<ResetPassword />} />
                <Route path= "/register" element ={<Register />} />
                <Route path= "/news" element ={<News />} />
                <Route path= "/news/:id" element ={<NewsElem />} />
                <Route path= "/projects" exact element ={<Projects />} />
                <Route path= "/projects/:id" element ={<Project />} />
                <Route path= "/about" element ={<AboutUs />} />
            </Routes>
          </div>
        </Router>
        <Footer />
      </Box>
    </IntlProvider>
  );
}

const mapStateToProps = state => {
  return { language: state.selectedLanguage.language };
};
export default connect(mapStateToProps, {}) (App);

