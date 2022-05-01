import React, { useEffect, useState } from "react"
// import { useState } from "react";
//Redirect replace by Naviagate
import {useNavigate} from 'react-router-dom'
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
import config from "../config";
import useFetch from 'use-http';
import { setLoginOK, setAppError,setLoggedUser, setNotifNumber} from '../redux/actions'
import { connect } from 'react-redux'

//InputGroup-> cada input pode ter 3 elementos
//1. um simbolo ilucidativo do que é para colocar em cada campo (à esquerda)
//2. caixa de texto propriamente dtia com o tipo de dados com placeholder e a variável do resultado
//3. algum elemento à direita (por exemplo o botão para ver/esconder a password)

function Login ({setLoginOK,setAppError,setLoggedUser,setNotifNumber,errorTopBar="",...props}) {
    const [firstName, setFirstName] = useState("User_Name");
    const [myPrivileges, setMyPrivileges] = useState("VIEWER");
 

    //simboloas usados na pagina de login
    const UserSymbol = chakra(FaUserAlt);
    const LockSymbol = chakra(FaLock);
    const EyeSymbol = chakra(FaEye);
    const EyeSlashSymbol = chakra(FaEyeSlash);

    const navigate = useNavigate();


    useEffect(()=>{
        const authString = localStorage.getItem("Authorization")
        if (authString && authString!=""){
           navigate("/");
        //    console.log("está logado e tenho que arranjar maneira de redireccionar daqui")
        }
        setAppError("");

    },[])


    // const option={
    //     method: "GET",
    //     headers: {
    //         Authorization: localStorage.getItem("Authorization"),
    //         Accept: "application/json",
    //         "Content-Type": "application/json"
    //     }
    // }
    //config.API_URL

    //função para fazer o request ao servidor
    const { get, post, response, loading, error } = useFetch();

    //funções onSubmit e onError são usadas para o useForm, que lê os dados introduzidos nas caixas de input e os coloca num objecto 
    const {register, handleSubmit, formState: {errors}}= useForm();

    async function onSubmit (data, e) {
        //console.log(data, e);
        // const options ={
        //     method: "POST",
        //     headers: {
        //         "Content-Type": "application/json",
        //     },
        //     accept: "application/json",
        //     body: JSON.stringify(data),
        // }
        const authString = await post('users/login', data)
        if (response.ok) {
            localStorage.setItem("Authorization", authString.Authorization);
            setLoginOK(authString.Authorization);
            console.log(authString.Authorization);
            setAppError("");
           

            const myProfile = await get('/users/myProfile');
            if (response.ok) {
            //const myProfile =response.data;
                localStorage.setItem("firstName", myProfile.firstName);
                localStorage.setItem("privileges", myProfile.privileges);
                localStorage.setItem("userId", myProfile.id);
                //setLoginOK(authString.Authorization);
                setFirstName(myProfile.firstName);
                setMyPrivileges(myProfile.privileges);
                setLoggedUser(myProfile.firstName, myProfile.privileges, myProfile.id)
                setAppError("");
            } else if(response.status==401) {
                console.log("credenciais erradas? " + error)
                setAppError('error_fetch_generic');
            }else{
                console.log("houve um erro no fetch " + error)
                if(error && error!=""){
                setAppError(  error);
                }else{
                    setAppError(  "error_fetch_generic" );
                }
            }

            const numNotif = await get('notifications/unreadNumber');
            if (response.ok) {
                setNotifNumber(numNotif);
                setAppError("");
            } else if(response.status==401) {
                console.log("credenciais erradas? " + error)
                setAppError('error_fetch_generic');
            }else{
                console.log("houve um erro no fetch " + error)
                if(error && error!=""){
                    setAppError(  error);
                }else{
                    setAppError(  "error_fetch_generic" );
                }
            }


            navigate("/");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_login_401');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
            setAppError(  error );
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }
      
       


    }

    
    const onError = (errors, e) => console.log(errors, e);

    // const [data, setData]= useState("");
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
                            <Input {...register("email", {required: true,setValueAs: (v)=> v.trim()})} type="email" placeholder={intl.formatMessage({id: 'form_field_email'})} />
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
                                {...register("password", {required: true,setValueAs: (v)=> v.trim()})}
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
    
const mapStateToProps = state => {
    return { errorTopBar: state.errorMsg.errorTopBar, // fazemos a logica se ele existir mostra mensagem de erro
    };
};
    
export default connect(mapStateToProps, { setLoginOK,setAppError ,setLoggedUser,setNotifNumber})(Login);