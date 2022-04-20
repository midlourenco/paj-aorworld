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

import {
  ChakraProvider,
  Flex,
  Text,
  Link as ChakraLink,
  Box,
  Grid,
  HStack,
  StackDivider
} from "@chakra-ui/react";

import Home from './pages/Home';
import Login from './pages/Login';
import News from './pages/News';
import Projects from './pages/Projects';
import Project from './pages/Projects';
import AboutUs from './pages/AboutUs';
import Header from "./components/sections/Header";
//se dentro da pasta pages eu colocar um index com o export das outras páginas devo poder fazer o seguinte:
// import {Home, Login, News, Projects, AboutUs} from './pages'


export const myThemeProvider = ({ children }) => {
  return <ChakraProvider>{children}</ChakraProvider>;
};

function App() {


  const NavLink = ({ text }) => (
    <ChakraLink>
      <Text fontSize="xl">{text}</Text>
    </ChakraLink>
  );
  const NavBar = () => (
    <HStack spacing={3} divider={<StackDivider />} as="nav">
      <NavLink text="Home" to= "/" /> 
      <NavLink text="Login" to= "/login"/>
      <NavLink text="News" to= "/news" />
      <NavLink text="Projects" to= "/projects"/>
      <NavLink text="About Us" to= "/about"/>
    </HStack>
  );
  return (
   
      <Router>
        <div className="App">
          <h1> Teste de react Router </h1>
          <Header />
          <NavBar />

          <Routes>
              <Route path= "/" exact element ={<Home />} />
              <Route path= "/login" element ={<Login />}/>
              <Route path= "/news" element ={<News />} />
              <Route path= "/projects" element ={<Projects />} />
              <Route path= "/projects/:id" element ={<Project />} />
              <Route path= "/about" element ={<AboutUs />} />
          </Routes>
        </div>
      </Router>
  
  );
}

export default App;

