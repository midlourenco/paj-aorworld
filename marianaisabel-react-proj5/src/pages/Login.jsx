//Redirect replace by Naviagate
import {Navigate} from 'react-router-dom'

import { useState } from "react";
//https://react-hook-form.com/
import { useForm } from "react-hook-form";

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
    InputRightElement
} from "@chakra-ui/react";

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { FaUserAlt, FaLock,FaEyeSlash, FaEye} from "react-icons/fa";

const UserSymbol = chakra(FaUserAlt);
const LockSymbol = chakra(FaLock);
const EyeSymbol = chakra(FaEye);
const EyeSlashSymbol = chakra(FaEyeSlash);

//InputGroup-> cada input pode ter 3 elementos
//1. um simbolo ilucidativo do que é para colocar em cada campo (à esquerda)
//2. caixa de texto propriamente dtia com o tipo de dados com placeholder e a variável do resultado
//3. algum elemento à direita (por exemplo o botão para ver/esconder a password)

const Login = (props) =>{
   // console.log(props);
   // props.history
   //exemplo para redireccionar para uma página dada uma dada condição:
    // if(true){
    //     return <Navigate to="/news" />
    // }

    const {register, handleSubmit, formState: {errors}}= useForm();
    //const onSubmit = values => console.log(values);

    const [data, setData]= useState("");
    //mostrar e esconder a password
    const [showPassword, setShowPassword] = useState(false);
    //função que chamamos ao clicar em "show"/"hide" password - altera o state de false para true e vice-versa
    const handleShowClick = () => setShowPassword(!showPassword);

    return (
        <Flex
            flexDirection="column"
            width="100wh"
            height="100vh"
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
            <Avatar bg="teal.500" />
            <Heading color="teal.400">Welcome</Heading>
            <Box minW={{ base: "90%", md: "468px" }}>
                <form onSubmit={handleSubmit((data)=>setData(JSON.stringify(data), console.log(data)))}>
                    <Stack
                        spacing={4}
                        p="1rem"
                        backgroundColor="whiteAlpha.900"
                        boxShadow="md"
                    >
                    <FormControl>
                        <InputGroup> 
                            <InputLeftElement
                                pointerEvents="none"
                                children={<UserSymbol color="gray.300" />}
                            />
                            <Input {...register("email")} type="email" placeholder="email address" />
                            {errors.email && errors.email.message}

                        </InputGroup>

                    </FormControl>
                    <FormControl>
                        <InputGroup>
                            <InputLeftElement
                                pointerEvents="none"
                                color="gray.300"
                                children={<LockSymbol color="gray.300" />}
                            />
                            <Input
                                {...register("password", {required: "Required"})}
                                type={showPassword ? "text" : "password"}
                                placeholder="Password"
                            />
                            <InputRightElement width="4.5rem">
                                <Button h="1.75rem" size="sm" onClick={handleShowClick}>
                                    {/* {showPassword ? "Hide" : "Show"} */}
                                    {showPassword ? <EyeSlashSymbol color = "gray.400" /> :<EyeSymbol color = "gray.300" /> }
                                </Button>
                            </InputRightElement>
                            {errors.password && errors.password.message}

                        </InputGroup>

                        <FormHelperText textAlign="right">
                            <Link>forgot password?</Link>
                        </FormHelperText>

                    </FormControl>
                    <Button
                        borderRadius={0}
                        type="submit"
                        variant="solid"
                        colorScheme="teal"
                        width="full"
                    >
                        Login
                    </Button>
              
                    </Stack>
                </form>
                </Box>
            </Stack>
            <Box>
                Create a new account?{" "}
                <Link color="teal.500" href="#">
                Sign Up
                </Link>
            </Box>
        </Flex>
    );
};
    
    
    
export default Login;