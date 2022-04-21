import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams
} from "react-router-dom";

import { useState } from "react";
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import messages from '../translations';
import {IntlProvider, FormattedMessage} from "react-intl";

import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    InputLeftElement,
    chakra,
    Grid,
    Box,
    Image,
    Badge,
    Text,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    InputRightElement,
    HStack,
    Spacer
} from "@chakra-ui/react";

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { RiNewspaperLine} from "react-icons/ri";
import { BiEraser} from "react-icons/bi";


function  ProjectCard ({projectElem, ...props}){

    let { id } = useParams();

    const project = {
        imageUrl: projectElem.imageUrl,
        title:  projectElem.title,
        description:  projectElem.description,
        keywords:  projectElem.keywords,
        users: projectElem.users,
        news:  projectElem.news,
        createdBy:  projectElem.createdBy,
        createdDate:  projectElem.createdDate,
        lastModifBy:  projectElem.lastModifBy,
        lastModifDate:  projectElem.lastModifDate,
    }

    //
    return (
        <Box maxW='sm' borderWidth='1px' borderRadius='lg' overflow='hidden' backgroundColor="white"> 

            <HStack display='flex' justifyContent="center"  alignItems="center">
            <Image src={project.imageUrl} alt={project.title.slice(0,10)} />
            </HStack>
            <Box p='6'>
                <Box display='flex' alignItems='baseline'>
                    <HStack >
                    {project.keywords.map(n => (
                        <Badge borderRadius='full' px='2' colorScheme='teal' >{n}</Badge>
                    ))}
                    </HStack>
                    <Box
                        color='gray.500'
                        fontWeight='semibold'
                        letterSpacing='wide'
                        fontSize='xs'
                        textTransform='uppercase'
                        ml='2'
                        >
                        {project.users.length} membros &bull; {project.news.length} news
                    </Box>
                </Box>
        
                <Box
                    mt='1'
                    fontWeight='semibold'
                    as='h4'
                    lineHeight='tight'
                    isTruncated
                >
                    {project.title}
                </Box>
        
                <Box  isTruncated>
                    {project.description}
                </Box>

                <Box display='flex' justifyContent="right" mt='2' alignItems='center'>
                {project.lastModifBy && project.lastModifBy!="" 
                ?   <Text  as='i' fontSize='sm'> {<lastModifBySymbol color="gray.200" />} Atualizado por: {project.lastModifBy}, em {project.lastModifDate} </Text>
                :   <Text  as='i' fontSize='sm' >{<createBySymbol color="gray.200" />} Escrito por: {project.createdBy}, em {project.createdDate} </Text>
                }
                </Box>
            </Box>
        </Box>





    )
    
    }
    export default ProjectCard;