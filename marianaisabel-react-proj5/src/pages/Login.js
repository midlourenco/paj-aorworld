import React from "react"
import { useState } from "react";
//Redirect replace by Naviagate
import {Navigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";

import { FormattedMessage ,useIntl} from "react-intl";

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
    Link,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    InputRightElement
} from "@chakra-ui/react";

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { FaUserAlt, FaLock,FaEyeSlash, FaEye} from "react-icons/fa";



//InputGroup-> cada input pode ter 3 elementos
//1. um simbolo ilucidativo do que é para colocar em cada campo (à esquerda)
//2. caixa de texto propriamente dtia com o tipo de dados com placeholder e a variável do resultado
//3. algum elemento à direita (por exemplo o botão para ver/esconder a password)

function Login () {
    const UserSymbol = chakra(FaUserAlt);
    const LockSymbol = chakra(FaLock);
    const EyeSymbol = chakra(FaEye);
    const EyeSlashSymbol = chakra(FaEyeSlash);
 

    const {register, handleSubmit, formState: {errors}}= useForm();
    const onSubmit = (data, e) => console.log(data, e);
    const onError = (errors, e) => console.log(errors, e);
  
    const [data, setData]= useState("");
    //mostrar e esconder a password
    const [showPassword, setShowPassword] = useState(false);
    //função que chamamos ao clicar em "show"/"hide" password - altera o state de false para true e vice-versa
    const handleShowClick = () => setShowPassword(!showPassword);
 
    //https://stackoverflow.com/questions/39630620/react-intl-how-to-use-formattedmessage-in-input-placeholder
    const intl = useIntl();

    return (
        <Flex
            flexDirection="column"
            width="100wh"
            minHeight="83vh"
            backgroundColor="gray.200"
            justifyContent="center"
            alignItems="center"
        >
            <Stack
                flexDir="column"
                mb="2"
                justifyContent="center"
                alignItems="center"
            >
            <Avatar bg="teal.500" mt={20}/>
            <Heading color="teal.400"> {intl.formatMessage({id: 'welcome'})}</Heading>
            <Box minW={{ base: "90%", md: "468px" }}>
                <form onSubmit={ handleSubmit(onSubmit,onError )}>
                    <Stack
                        spacing={4}
                        p="1rem"
                        backgroundColor="whiteAlpha.900"
                        boxShadow="md"
                    >
                    <FormControl isInvalid = {errors.email}>
                        <InputGroup> 
                            <InputLeftElement
                                pointerEvents="none"
                                children={<UserSymbol color="gray.300" />}
                            />
                            <Input {...register("email", {required: true})} type="email" placeholder={intl.formatMessage({id: 'form_field_email'})} />
                        </InputGroup>
                        {(errors.email)? 
                        (<FormErrorMessage><FormattedMessage id={"error_missing_email"}  ></FormattedMessage></FormErrorMessage>)
                        : null 
                        }
                    </FormControl>
                    <FormControl isInvalid = {errors.password}>
                        <InputGroup>
                            <InputLeftElement
                                pointerEvents="none"
                                color="gray.300"
                                children={<LockSymbol color="gray.300" />}
                            />
                            <Input
                                {...register("password", {required: true})}
                                type={showPassword ? "text" : "password"}
                                placeholder={intl.formatMessage({id: 'form_field_password'})}
                            />
                            <InputRightElement width="4.5rem">
                                <Button h="1.75rem" size="sm" onClick={handleShowClick}>
                                    {/* {showPassword ? "Hide" : "Show"} */}
                                    {showPassword ? <EyeSlashSymbol color = "gray.400" /> :<EyeSymbol color = "gray.300" /> }
                                </Button>
                            </InputRightElement>
                        </InputGroup>
                        {(errors.password)? 
                        (<FormErrorMessage><FormattedMessage id={"error_missing_password"}  ></FormattedMessage></FormErrorMessage>)
                        : null 
                        }
                        <FormHelperText textAlign="right">
                            <Link href="/reset_password">{intl.formatMessage({id: 'forgot_password'})}</Link>
                        </FormHelperText>

                    </FormControl>
                    <Button
                        borderRadius={0}
                        type="submit"
                        variant="solid"
                        colorScheme="teal"
                        width="full"
                        // onClick={console.log("carreguei em login")}
                    >
                        {intl.formatMessage({id: 'login'})} 
                    </Button>
              
                    </Stack>
                </form>
                </Box>
            </Stack>
            <Box mb={20}>
                {intl.formatMessage({id: 'create_new_account'})}{" "}
                <Link color="teal.500" href="/register">
                {intl.formatMessage({id: 'sign_up'})}
                </Link>
            </Box>
        </Flex>
    );
};
    
    
    
export default Login;