import React from "react";
import { connect } from 'react-redux'
import { chageTargetUser } from '../redux/actions'
import {
    Flex,
    Text,
    Link as ChakraLink,
    Heading,
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
    useMediaQuery,Badge,
    IconButton
  } from "@chakra-ui/react";

//vamos descontruir o props, e retirar o titulo e valor, e manter os restantes caracteristicas do props no props
function Card ({title="Titulo Default", value=99999,...props}) {


    // return (
    //     <div className="card">
    //         <div className="cardItem">
    //             <h4 className="statTilte">{title}</h4>
    //             <p className="statValue">{value}</p>
    //         </div>            
    //     </div>
    // );
    return(
        
    
              <Box 
              p={5} shadow='md' 
              borderWidth='1px' 
              background={"whiteAlpha.900"}
              {...props}>
                <Heading fontSize='xl'>{title}</Heading>
                <Text mt={4}>{value}</Text>
              </Box>
    
    )
    
}
export default Card;
//export default connect(null, {  })(Card);



// class Card extends React.Component {
//     render() {
//         return (
//         <div className="card">
//             <div className="cardItem">
//                 <h4 className="statTilte">Total de Utilizadores Registados{/*cardTitle*/}</h4>
//                 <p className="statValue">9999{/*chageTargetUser*/}</p>
//             </div>            
//         </div>
//     );
//     }
// }
