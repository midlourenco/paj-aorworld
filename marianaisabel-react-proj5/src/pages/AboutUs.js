import { useState } from "react";

import {
    ChakraProvider,
    Flex,
    Text,
    Link as ChakraLink,
    Box,
    Grid,
    Image,
    AspectRatio,
    Stack,
    HStack,
    Select,
    Button,
    Spacer,
    StackDivider
  } from "@chakra-ui/react";
  import ButtonExemple from "../components/ButtonExemple";

const AboutUs = ()=>{
    return (
        <Box>
            <Box>
                <ButtonExemple />
            </Box>
            <h1> A nossa equipa </h1>
            
            <Image
            borderRadius='full'
            boxSize='150px'
            src='https://bit.ly/dan-abramov'
            alt='nome do membro'
            />
             <Text>
                Nome:  Carlos Pita
                Email:  carlopita@aorprojects.pt
                Biografia:  uieyish ashdjkho  ewhiorh whro iwo3
            </Text>
             <Image
            borderRadius='full'
            boxSize='150px'
            src='https://bit.ly/dan-abramov'
            alt='nome do membro'
            />
             <Image
            borderRadius='full'
            boxSize='150px'
            src='https://bit.ly/dan-abramov'
            alt='nome do membro'
            />
             <Image
            borderRadius='full'
            boxSize='150px'
            src='https://bit.ly/dan-abramov'
            alt='nome do membro'
            />

            <h1> Onde estamos? </h1>
            <AspectRatio ratio={21 / 9} maxW='60%'>
            <iframe 
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d16751.422199276334!2d-8.433388030438046!3d40.18608913770532!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd22f9952aaf6517%3A0xd3822ec1d359eb52!2sDEI%20-%20Department%20of%20Informatics%20Engineering%20-%20FCTUC!5e0!3m2!1sen!2spt!4v1650556009804!5m2!1sen!2spt" 
            loading="lazy" 
            referrerpolicy="no-referrer-when-downgrade"
            alt="localizacao"
            ></iframe>
            </AspectRatio>
        </Box>
    )
    
}
export default AboutUs;