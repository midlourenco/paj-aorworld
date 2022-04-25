import React from "react"
import { useState } from "react";
import { Link } from "react-router-dom"
import { FormattedMessage} from "react-intl";
import {
  Flex,
  Text,
  Link as ChakraLink,
  Box,
  HStack,
  Select,
  Button,
  Spacer,
  Menu,
  MenuButton,
  MenuList,
  MenuItem,
  StackDivider,
  useMediaQuery,
  IconButton
} from "@chakra-ui/react";
import { CloseIcon, HamburgerIcon , ChevronDownIcon} from '@chakra-ui/icons'
import { setSelectedLanguage} from '../../redux/actions'
import { connect } from "react-redux";


// const MenuItems = (props) => {
//   const { children, isLast, to = "/", ...rest } = props
//   return (
//     <Text
//       mb={{ base: isLast ? 0 : 8, sm: 0 }}
//       mr={{ base: 0, sm: isLast ? 0 : 8 }}
//       display="block"
//       {...rest}
//     >
//       <Link to={to}>{children}</Link>
//     </Text>
//   )
// }

const NavLink = ({ path, text }) => (
  <ChakraLink as={Link} to ={path} >
    <Text fontSize="xl" > <FormattedMessage id={text} /></Text>
  </ChakraLink>
);


const MenuLoggedUser=()=>{
  return (
    <Menu>
      <MenuButton as={Button} colorScheme="teal" rightIcon={<ChevronDownIcon />}  >
        Nome
      </MenuButton>
      <MenuList color={"teal"}>
        <MenuItem><NavLink text="profile" path= "/profile"  /></MenuItem>
        <MenuItem><NavLink text="notifications" path= "/notification"  /></MenuItem>
        <MenuItem> <NavLink text="logout" path= "/logout"  /></MenuItem>
      </MenuList>
    </Menu>
  )
}


const HorizontalMenuBar=()=>{
  return(<HStack spacing={6} divider={<StackDivider />} as="nav" margin={4}>
    <NavLink text="home"  path= "/" /> 
    <NavLink text="news" path= "/news" />
    <NavLink text="projects" path= "/projects" />
    <NavLink text="about_us" path= "/about"/>
  </HStack>)
}

const VerticalMenuBar= ()=>{

  return(<Menu m={5} >
      <MenuButton as={IconButton} colorScheme="teal" icon={<HamburgerIcon />} m={5}  />
      <MenuList color={"teal"}>
        <MenuItem key="home"><NavLink text="home"  path= "/" /> </MenuItem>
        <MenuItem key="news"><NavLink text="news" path= "/news" /></MenuItem>
        <MenuItem key="projects"><NavLink text="projects" path= "/projects" /></MenuItem>
        <MenuItem key="aboutus"> <NavLink text="about_us" path= "/about"/> </MenuItem>
      </MenuList>
    </Menu>

  )


}


function Header ({setSelectedLanguage,language,...props}) {
  const [login, setLogin]= useState(false);

  //regarding languages switchingi 
  //const [locale, setLocale] = useState(language)
  const handleSelect= e => {
    //setLocale(e.target.value);
    setSelectedLanguage(e.target.value);
    localStorage.setItem("selectedLang",e.target.value );
    let langLS= localStorage.getItem("selectedLang");
    console.log("lang do browser" + navigator.language)
  }
  

  const langArray = ["en", "pt"];
  function flag(lang){
    switch(lang){
      case "en": return "🇬🇧 "; break;
      case "pt": return "🇵🇹 "; break;
    }
  }

  const [isMobile] = useMediaQuery("(max-width: 768px)") 


  return(
    <Box backgroundColor="teal.400" color={"white"}>  
    <Flex display="flex" verticalAlign={"middle"} justifyContent={"center"}>
    {isMobile ? <VerticalMenuBar />  : <HorizontalMenuBar />}
    <Spacer />
      
      <HStack spacing={3} margin={4}>
        <Select 
        onChange={handleSelect} 
        defaultValue={language} 
        variant='unstyled' 
        bg="teal.400"
        borderColor="teal.400"
        size='md'  
        h={10} 
        width={20} 
        cursor="pointer" 
        _hover={{ bg: "teal.600" }}
        >
          {langArray.map(l => (
            <option key={l} value={l} className= "blackText" >{flag(l)}{l}</option>
          ))}
        </Select>
        {/* <Button colorScheme='teal' mr='4'><FormattedMessage id="sign_up" /></Button> */}
        {login ?
          <Button colorScheme='teal' onClick={()=>setLogin(!login)}>
            <NavLink text="login" path= "/login"  />
          </Button>
          : <MenuLoggedUser />
        }
    </HStack>
  </Flex>

</Box>
  )




  // return (
  //   <Flex
  //     as="nav"
  //     align="center"
  //     justify="space-between"
  //     wrap="wrap"
  //     w="100%"
  //     mb={8}
  //     p={8}
  //     bg={["primary.500", "primary.500", "transparent", "transparent"]}
  //     color={["white", "white", "primary.700", "primary.700"]}
  //     {...props}
  //   >
  //     <Flex align="center">
  //       {/* <Banner
  //         w="100px"
  //         color={["white", "white", "primary.500", "primary.500"]}
  //       /> */}
  //     </Flex>

  //     <Box display={{ base: "block", md: "none" }} onClick={toggleMenu}>
  //       {/* {show ? <CloseIcon /> : <MenuIcon />} */}
  //     </Box>

  //     <Box
  //       display={{ base: show ? "block" : "none", md: "block" }}
  //       flexBasis={{ base: "100%", md: "auto" }}
  //     >
  //       <Flex
  //         align={["center", "center", "center"]}
  //         justify={["center", "space-between", "flex-end"]}
  //         direction={["column", "row", "row"]}
  //         paddingTop={[4, 4, 0, 0]}
  //       >
  //         <MenuItems to="/">Home</MenuItems>
  //         <MenuItems to="/how">How It works </MenuItems>
  //         <MenuItems to="/faetures">Features </MenuItems>
  //         <MenuItems to="/pricing">Pricing </MenuItems>
  //         <MenuItems to="/signup" isLast>
  //           <Button
  //             size="sm"
  //             rounded="md"
  //             color={["primary.500", "primary.500", "white", "white"]}
  //             bg={["white", "white", "primary.500", "primary.500"]}
  //             _hover={{
  //               bg: [
  //                 "primary.100",
  //                 "primary.100",
  //                 "primary.600",
  //                 "primary.600",
  //               ],
  //             }}
  //           >
  //             Create Account
  //           </Button>
  //         </MenuItems>
  //       </Flex>
  //     </Box>
  //   </Flex>
  
}


const mapStateToProps = state => {
  return { language: state.selectedLanguage.language };
};

export default  connect(mapStateToProps, {setSelectedLanguage}) (Header);