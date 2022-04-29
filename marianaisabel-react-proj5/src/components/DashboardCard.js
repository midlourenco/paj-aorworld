import React from "react";
import { connect } from 'react-redux'
import { chageTargetUser } from '../redux/actions'


//vamos descontruir o props, e retirar o titulo e valor, e manter os restantes caracteristicas do props no props
function Card ({title="Titulo Default", value=99999,...props}) {


    return (
        <div className="card">
            <div className="cardItem">
                <h4 className="statTilte">{title}</h4>
                <p className="statValue">{value}</p>
            </div>            
        </div>
    );
    
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
