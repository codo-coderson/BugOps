<odoo>
  <data>  
    <record id="vista_tipo_tree" model="ir.ui.view">
		<field name="name">vista.tipo.tree</field>
		<field name="model">moduloafa.tipo</field>
		<field name="arch" type="xml">
			<tree string="tipos">
				<field name="nombre"/>
				<field name="descripcion"/>
			</tree>
		</field>
	</record>
	
	<record id="vista_tipo_form" model="ir.ui.view">
		<field name="name">vista.tipo.form</field>
		<field name="model">moduloafa.tipo</field>
		<field name="arch" type="xml">
			<form string="tipos">
				<sheet>
					<group>
						<field name="nombre"/>
						<field name="descripcion"/>
					</group>
				</sheet>
			</form>
		</field>
	</record>
  
	<record id="vista_producto_tree" model="ir.ui.view">
		<field name="name">vista.producto.tree</field>
		<field name="model">moduloafa.producto</field>
		<field name="arch" type="xml">
			<tree string="productos">
				<field name="nombre"/>
				<field name="precio"/>
				<field name="fecha"/>
				<field name="lugar"/>
			</tree>
		</field>
	</record>
		
	<record id="vista_producto_form" model="ir.ui.view">
		<field name="name">vista.producto.form</field>
		<field name="model">moduloafa.producto</field>
		<field name="arch" type="xml">
			<form string="productos">
				<sheet>
					<group>
						<field name="nombre"/>
						<field name="precio"/>
						<field name="fecha"/>
						<field name="lugar"/>
					</group>
				</sheet>
			</form>
		</field>
	</record>
  
  	<record id="action_tipo" model="ir.actions.act_window">
		<field name="name">Tipos</field>
		<field name="res_model">moduloafa.tipo</field>
		<field name="view_type">form</field>
		<field name="view_mode">list,form</field>
	</record>
	
	<record id="action_producto" model="ir.actions.act_window">
		<field name="name">Productos</field>
		<field name="res_model">moduloafa.producto</field>
		<field name="view_type">form</field>
		<field name="view_mode">list,form</field>
	</record>
	
	<menuitem id="menuppal_moduloAFA" name="moduloAFA" parent="base.menu_management" sequence="300"/>
	<menuitem id="menutipo_moduloAFA" name="Tipos" parent="menuppal_moduloAFA" action="action_tipo" sequence="1"/>
	<menuitem id="menuproducto_moduloAFA" name="Productos" parent="menuppal_moduloAFA" action="action_producto" sequence="2"/>
  </data>
</odoo>