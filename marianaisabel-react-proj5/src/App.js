import React from "react";
import './style.css';
//In react-router-dom v6, "Switch" is replaced by routes "Routes".
//https://stackoverflow.com/questions/63124161/attempted-import-error-switch-is-not-exported-from-react-router-dom
import { 
    BrowserRouter as Router,
    Route, 
    Routes,
    Link
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
import customTheme from "./theme.js";

import Home from './pages/Home';
import Login from './pages/Login';
import News from './pages/News';
import Projects from './pages/Projects';
import AboutUs from './pages/AboutUs';

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
      <Link to= "/"> 
        <NavLink text="Home" /> 
      </Link>
      <Link to= "/login"> 
        <NavLink text="Login" />
      </Link>
      <Link to= "/news"> 
      <NavLink text="News" />
      </Link>
      <Link to= "/projects"> 
        <NavLink text="Projects" />
      </Link>
      <Link to= "/about"> 
        <NavLink text="About Us" />
      </Link>
    </HStack>
  );
  return (
    <ChakraProvider theme={customTheme}>
      <Router>
        <div className="App">
          <h1> Teste de react Router </h1>

          <NavBar />

          <Routes>
              <Route path= "/" exact element ={<Home />} />
              <Route path= "/login" element ={<Login />}/>
              <Route path= "/news" element ={<News />}/>
              <Route path= "/projects" element ={<Projects />} />
              <Route path= "/about" element ={<AboutUs />} />
          </Routes>
        </div>
      </Router>
    </ChakraProvider>
  );
}

export default App;

