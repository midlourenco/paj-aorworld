import React from "react"
//import bannerLogo from '../images/bannerLogo.png'

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
  Flex,
  Link as ChakraLink,
  Box,
  Image,
  Stack
} from "@chakra-ui/react";
import { FormattedMessage} from "react-intl";


const Home = ()=>{
    
return (
    <Box >
    <Image width="100%" objectFit='cover' src="images/bannerLogo.png" alt="Logo" />
        <Flex
            flexDirection="column"
            width="100wh"
            minHeight="60vh"
            backgroundColor="gray.200"
            justifyContent="center"
            alignItems="center"
            
        >
        
        <Box minW={{ base: "90%", md: "468px" }} my={30}>
        <Stack
            spacing={4}
            padding="5rem"
            backgroundColor="whiteAlpha.900"
            boxShadow="md"
            width={"70%"}
            margin="auto"
        >
        <FormattedMessage id= "intro_home" />
        </Stack>
        </Box>
        
    
        </Flex>
    </Box> 
            
           
    )
    
    }
    export default Home;