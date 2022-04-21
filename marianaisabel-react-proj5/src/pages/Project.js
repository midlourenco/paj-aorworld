import React from "react"
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
    Box,
    Image,
    Badge,
    Link,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    InputRightElement
} from "@chakra-ui/react";

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { RiNewspaperLine} from "react-icons/ri";
import { BiEraser} from "react-icons/bi";

function Project(){
    const createBySymbol = chakra(RiNewspaperLine);
    const lastModifBySymbol = chakra(BiEraser);


   
    const news = {
        imageUrl: 'https://rockcontent.com/br/wp-content/uploads/sites/2/2020/02/projeto-pessoal.png',
        title: 'AoR - Lista de Atividades',
        description: 'Projecto PAJ - desenvolvido em Java, com uma API REST, persistência de dados em base de dados em mySQL e frontend em ReactJS, javaScript, CSS e HTML.',
        keywords: ['Java', 'Programar', 'React'],
        users: ['Java', 'Programar', 'Programar', 'Programar', 'Programar', 'React'],
        projects: ['Java', 'Programar',  'Programar','React'],
        createdBy: 'Mariana',
        createdDate: '15-04-2022',
        lastModifBy: 'Isabel',
        lastModifDate: '21-04-2022'
    }

    return (
        <Box maxW='sm' borderWidth='1px' borderRadius='lg' overflow='hidden'>
            <Image src={news.imageUrl} alt={news.title.slice(0,10)} />

            <Box p='6'>
                <Box display='flex' alignItems='baseline'>
                {news.keywords.map(n => (
                    <Badge borderRadius='full' px='2' colorScheme='teal'>{n}</Badge>
                ))}
                
                    <Box
                        color='gray.500'
                        fontWeight='semibold'
                        letterSpacing='wide'
                        fontSize='xs'
                        textTransform='uppercase'
                        ml='2'
                        >
                        {news.users.length} membros &bull; {news.projects.length} projects
                    </Box>
                </Box>
        
                <Box
                    mt='1'
                    fontWeight='semibold'
                    as='h4'
                    lineHeight='tight'
                    isTruncated
                >
                    {news.title}
                </Box>
        
                <Box>
                    {news.description}
                </Box>

                <Box display='flex' mt='2' alignItems='center'>
                    {<createBySymbol color="gray.400" />} Escrito por: {news.createdBy}, em {news.createdDate} 
                    {<lastModifBySymbol color="gray.400" />} Atualizado por: {news.lastModifBy}, em {news.lastModifDate} 
                </Box>
                </Box>
            </Box>
    )
}

export default Project;