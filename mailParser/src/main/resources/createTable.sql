CREATE TABLE public.data_type (
  id INTEGER NOT NULL,
  key INTEGER NOT NULL,
  name  VARCHAR(255) NOT NULL,
  address  VARCHAR(255) NOT NULL,
  
  CONSTRAINT user_data_pkey PRIMARY KEY(id)
);

INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  101,  101,  'accountmanageremail.com',  'Account');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  102,  102,  'link.toysrus.com',  'Toys');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  103,  103,  'online.nationalgridus.com',  'Confection');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  104,  104,  'play.google.com',  'Toys');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  105,  105,  'shop.nordstrom.com',  'Confection');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  106,  106,  'squareup.com',  'Confection');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  107,  107,  'unipaygold.unibank.com',  'Bank');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  108,  108,  'www.amtrak.com',  'Confection');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  109,  109,  'www.capitalone.com',  'Bank');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  110,  110,  'www.hrsaccount.com',  'Bank');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  111,  111,  'www.infinitifinance.com',  'Account');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  112,  112,  'www.nstar.com',  'Toys');
INSERT INTO  public.data_type(  id,  key,  address, name) VALUES (  113,  113,  'www.united.com',  'Account');
