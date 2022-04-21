import React from "react";
import './style.css';
import bannerLogo from './images/bannerLogo.png'

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

//se dentro da pasta pages eu colocar um index com o export das outras páginas devo poder fazer o seguinte:
// import {Home, Login, News, Projects, AboutUs} from './pages'


export const myThemeProvider = ({ children }) => {
  return <ChakraProvider>{children}</ChakraProvider>;
};

function App() {
  //regarding languages switching
  const [locale, setLocale] = useState("en")
  const handleSelect= e => (
    setLocale(e.target.value)
  )
  const langArray = ["en", "pt"];
  const flagArray = ["🇬🇧 ","🇵🇹"];
  function flag(lang){
    switch(lang){
      case "en": return "🇬🇧 "; break;
      case "pt": return "🇵🇹 "; break;
    }
  }

  const NavLink = ({ path, text }) => (
    <ChakraLink as={Link} to ={path} >
      <Text fontSize="xl" > <FormattedMessage id={text} ></FormattedMessage></Text>
    </ChakraLink>
  );



  // <NavLink text="Home"/> 
  // <NavLink text="Login" to= "/login"/>
  // <NavLink text="News" to= "/news" />
  // <NavLink text="Projects" to= "/projects"/>
  // <NavLink text="About Us" to= "/about"/>
  
  const NavBar = () => (
    <Box backgroundColor="teal.400" color={"white"}>  
      <Flex>
        <HStack spacing={6} divider={<StackDivider />} as="nav" >
         
       
          <NavLink text="home"  path= "/"  /> 
          <NavLink text="news" path= "/news" />
          <NavLink text="projects" path= "/projects" />
          <NavLink text="about_us" path= "/about"/>
        </HStack>
        <Spacer />
        
        <HStack spacing={3}>
          <Select onChange={handleSelect} defaultValue={locale} variant='unstyled' size='md'  width={20}>
            {langArray.map(l => (
              <option key={l} value={l}>{flag(l)}{l}</option>
            ))}
          </Select>
          <Button colorScheme='teal' mr='4'><FormattedMessage id="sign_up" /></Button>
          <Button colorScheme='teal'><NavLink text="login" path= "/login" /></Button>
      </HStack>
    </Flex>
  
  </Box>
    
   
  );
  return (
    <IntlProvider locale={locale} messages ={messages[locale]}>

      <Router>
        <div className="App">
          <NavBar />

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
    </IntlProvider>
  );
}

export default App;

