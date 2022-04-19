import * as React from "react";
import './App.css';
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

import Home from './pages/Home';
import Login from './pages/Login';
import News from './pages/News';
import Projects from './pages/Projects';
import AboutUs from './pages/AboutUs';

// import {Home, Login, News, Projects, AboutUs} from './pages'

function App() {

  type NavLinkProps = { text: string };
  const NavLink = ({ text }: NavLinkProps) => (
    <ChakraLink>
      <Text fontSize="xl">{text}</Text>
    </ChakraLink>
  );
  const NavBar = () => (
    <HStack spacing={3} divider={<StackDivider />} as="nav">
      <Link to="/">
        <NavLink text="Home" />
      </Link>
      <Link to="/about">
        <NavLink text="About" />
      </Link>
    </HStack>
  );
  return (
    <ChakraProvider>
      <Router>
        <div className="App">
          <h1> Teste de react Router </h1>

          <NavBar />
          <Link to= "/"> Home</Link>
          <Link to= "/login"> Login</Link>
          <Link to= "/news"> News</Link>
          <Link to= "/projects"> Projects</Link>
          <Link to= "/aboutus"> About Us</Link>
        
          
          
          <Routes>
              <Route path= "/" exact element ={<Home />} />
              <Route path= "/login" element ={<Login />}/>
              <Route path= "/news" element ={<News />}/>
              <Route path= "/projects" element ={<Projects />} />
              <Route path= "/aboutus" element ={<AboutUs />} />
          </Routes>
         
        </div>
      </Router>
    </ChakraProvider>
  );
}

export default App;

