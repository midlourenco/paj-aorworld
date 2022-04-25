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


function SingleNews() {
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

        </Box>
    )
}
    
    
    
    export default SingleNews;