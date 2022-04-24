import React from "react"
import { useState } from "react";
//https://react-hook-form.com/
import { useForm } from "react-hook-form";

import { FormattedMessage} from "react-intl";
import {
    Link,
    NavLink,
    useParams,
    useLocation,
    Outlet,
    useSearchParams
  } from "react-router-dom";
  
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
    Link as ChakraLink,
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

function Project( ...props){
    const { id } = useParams();
    const queryString = new URLSearchParams(useLocation().search);
    console.log(queryString.get("id"))

    return (
        <Box maxW='sm' borderWidth='1px' borderRadius='lg' overflow='hidden'>
            dentro de um projecto  {id}  
        </Box>
    )
}

export default Project;