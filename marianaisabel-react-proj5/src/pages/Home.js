import React from "react"
import bannerLogo from '../images/bannerLogo.png'

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
import messages from '../translations'
import {IntlProvider, FormattedMessage} from "react-intl";


const Home = ()=>{
    
return (
<Box>
<Image width="100%" objectFit='cover' src={bannerLogo} alt="Logo" />
    <Flex
        flexDirection="column"
        width="100wh"
        height="60vh"
        backgroundColor="gray.200"
        justifyContent="center"
        alignItems="center"
    >
    
    <Box minW={{ base: "90%", md: "468px" }}>
    <Text> A requalificação e renovação de competências profissionais na área das novas tecnologias e competências digitais é um grande desafio.</Text>
      
    </Box>
    
   
    </Flex>
    </Box> 
            
           
    )
    
    }
    export default Home;