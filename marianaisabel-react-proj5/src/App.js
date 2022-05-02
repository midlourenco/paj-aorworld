import React, { useEffect,Suspense } from "react";
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
import { connect } from "react-redux";

import ErrorMsg from "./components/ErrorMsgTopBar";
import Home from './pages/Home';
import Login from './pages/Login';
import News from './pages/News';
import NewsArticle from "./pages/NewsArticle";
import NewNews from './pages/NewNews';
import Projects from './pages/Projects';
import Project from './pages/Project';
import NewProject from './pages/NewProject';
import AboutUs from './pages/AboutUs';
import Header from "./components/sections/Header";
import Register from "./pages/Register";
import ResetPassword from "./pages/ResetPassword";
import Footer from "./components/sections/Footer";
import ErrorPage404 from "./pages/ErrorPage404";
import Logout from "./pages/Logout";
import Profile from "./pages/Profile";
import Notification from "./pages/Notification";
import Dashboard from "./pages/Dashboard";
import UserManagement from "./pages/UserManagement";
import ProjectAssocUsers from "./pages/ProjectAssocUsers"

import config from "./config";
import useFetch, {Provider} from 'use-http';
//se dentro da pasta pages eu colocar um index com o export das outras páginas devo poder fazer o seguinte:
// import {Home, Login, News, Projects, AboutUs} from './pages'

//https://reactrouter.com/docs/en/v6/api :





//alternativa para erros no intl:
//onError={myCustomErrorFunction} 
/**
 * Para o provider IntlProvider ter acesso ao locale, vamos usar o redux para ir bucar esta informação 
 */
function App({language = "en",error="",...props}) {
  const options = {
    suspense: true ,// B. can put `suspense: true` here too
  }


  const globalOptions = {
    cachePolicy: 'no-cache',
		interceptors: {
      request: ({ options }) => {
        options.headers = {
          Accept: "application/json",
          "Content-Type": "application/json"
				}
        
        const token = localStorage.getItem("Authorization")

        if(token){
          options.headers.Authorization = token;
        }


        return options
      },
      response: ({ response }) => {
				console.log('initial resopnse.data', response.data)
				
        return response 
      }
    }
  }

 useEffect(()=>{

 },[error]);
 console.log("o erro no App é "+ error)
  console.log("language in app " + language)
  const locale=language;
  return (
    <IntlProvider locale={locale} messages ={messages[locale]}   >
      <Provider url={config.API_URL} options={globalOptions}>
      <Box>
      
        <Router basename="/marianaisabel-frontend-proj5" >
          <div className="App">
            <Header />
            <ErrorMsg />
            {/* {error && error!=""
            ? <ErrorMsg />
            : null
            } */}
            <Suspense fallback='Loading...' />
    
            <Routes>
                <Route path= "/" exact element ={<Home />} />
                <Route path= "/login" element ={<Login />}/>
                <Route path= "/logout" element ={<Logout />}/>
                <Route path= "/reset_password"  element ={<ResetPassword />} />
                <Route path= "/register" element ={<Register />} />
                <Route path= "/news" element ={<News />} />
                <Route path= "/news/new" element ={<NewNews />} />
                <Route path= "/news/:id" element ={<NewsArticle />} />
                <Route path= "/projects/new" element ={<NewProject />} /> 
                <Route path= "/projects/:id/associateusers" element ={<ProjectAssocUsers />} /> 
                <Route path= "/projects/:id" element ={<Project />} /> 
                <Route path= "/projects" element ={<Projects />} > </Route>
                <Route path= "/about" element ={<AboutUs />} />
                <Route path= "/profile" element ={<Profile />} />
                <Route path= "/profile/:id" element ={<Profile />} />
                <Route path= "/notification" element ={<Notification />} />
                <Route path= "/dashboard" element ={<Dashboard />} />
                <Route path= "/usermangement" element ={<UserManagement />} />
                <Route path= "*" element ={<ErrorPage404 />} />
            </Routes>
          </div>
        </Router>
        <Footer />
      </Box>
      </Provider>
    </IntlProvider>
  );
}

const mapStateToProps = state => {
  return { language: state.selectedLanguage.language, 
          
  };
};
export default connect(mapStateToProps, {}) (App);

